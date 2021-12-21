package com.kaizen.security.jwt;

import com.kaizen.security.UserRole;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.List.of;

/**
 * {@code AwsCognitoIdTokenProcessor} is the AWS Cognito Id Token Processor for
 * the application.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@Component
public class AwsCognitoIdTokenProcessor {
    /**
     * Represents the JWT configuration used by the application.
     */
    @Autowired
    private JwtConfiguration jwtConfiguration;

    /**
     * Represents the Configurable JWT Processor used by the application.
     */
    @Autowired
    private ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor;

    /**
     * Get authentication from the specific http servlet request.
     * 
     * @param request the request to get authntication from.
     * @throws Exception if an error occurs.
     * @return a jwt authentication from the request, else null.
     */
    public Authentication authenticate(HttpServletRequest request) throws Exception {
        String idToken = request.getHeader(jwtConfiguration.getHttpHeader());
        if (idToken != null) {
            JWTClaimsSet claims = configurableJWTProcessor.process(getBearerToken(idToken), null);
            validateIssuer(claims);
            verifyIfIdToken(claims);
            String username = getUserNameFrom(claims);
            if (username != null) {
                List<GrantedAuthority> grantedAuthorities = of(new SimpleGrantedAuthority(getRoleFrom(claims)));
                User user = new User(username, "", of());
                return new JwtAuthentication(user, claims, grantedAuthorities);
            }
        }
        return null;
    }

    /**
     * Get role from the specific JWT Claims Set.
     * 
     * @param claims the claims containings JWT's infomation.
     * @return a string of role.
     */
    private String getRoleFrom(JWTClaimsSet claims) {
        if (claims.getClaims().get(jwtConfiguration.getGroupsField()) == null) {
            return "ROLE_" + UserRole.USER.toString();
        }
        return "ROLE_" + (claims.getClaims().get(jwtConfiguration.getGroupsField()).toString().contains(
                this.jwtConfiguration.getAdminGroup()) ? UserRole.ADMIN.toString() : UserRole.USER.toString());
    }

    /**
     * Get user name from the specific JWT Claims Set.
     * 
     * @param claims the claims containings JWT's infomation.
     * @return a string of user name.
     */
    private String getUserNameFrom(JWTClaimsSet claims) {
        return claims.getClaims().get(jwtConfiguration.getUserNameField()).toString();
    }

    /**
     * Verify if the specific JWT Claims Set is id token.
     * 
     * @param claims the claims containings JWT's infomation.
     * @throws Exception if an error occurs.
     */
    private void verifyIfIdToken(JWTClaimsSet claims) throws Exception {
        if (!claims.getIssuer().equals(jwtConfiguration.getCognitoIdentityPoolUrl())) {
            throw new Exception("JWT Token is not an id Token");
        }
    }

    /**
     * Validate the specific JWT Claims Set's issuer.
     * 
     * @param claims the claims containings JWT's infomation.
     * @throws Exception if an error occurs.
     */
    private void validateIssuer(JWTClaimsSet claims) throws Exception {
        if (!claims.getIssuer().equals(jwtConfiguration.getCognitoIdentityPoolUrl())) {
            throw new Exception(String.format("Issuer %s does not match cognito idp %s", claims.getIssuer(),
                    jwtConfiguration.getCognitoIdentityPoolUrl()));
        }
    }

    /**
     * Get bearer token from the specific token.
     * 
     * @param token the token containing JWT's infomation.
     * @return a string of bearer token.
     */
    private String getBearerToken(String token) {
        final String bearer = "Bearer ";
        return token.startsWith(bearer) ? token.substring(bearer.length()) : token;
    }
}
