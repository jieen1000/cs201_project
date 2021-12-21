package com.kaizen.security.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * {@code JwtAuthentication} is the JWT authentication for the application.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
public class JwtAuthentication extends AbstractAuthenticationToken {
    /**
     * Represents the principal of the JWT authentication.
     */
    private final Object principal;

    /**
     * Represents the JWT claims set of the JWT authentication.
     */
    private JWTClaimsSet jwtClaimsSet;

    /**
     * Create a JWT authentication with the specific principal JWT claims set and
     * authorities. Set authenticated to true.
     * 
     * @param principal    the JWT authentication's principal.
     * @param jwtClaimsSet the JWT authentication's JWT claims set.
     * @param authorities  the JWT authentication's authorities.
     */
    public JwtAuthentication(Object principal, JWTClaimsSet jwtClaimsSet,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.jwtClaimsSet = jwtClaimsSet;
        super.setAuthenticated(true);
    }

    /**
     * Gets the credentials of the JWT authentication.
     * 
     * @return null.
     */
    public Object getCredentials() {
        return null;
    }

    /**
     * Gets the principal of the JWT authentication.
     * 
     * @return a object containing the principal.
     */
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * Gets the JWT claims set of the JWT authentication.
     * 
     * @return a JWTClaimsSet containing the JWT claims set.
     */
    public JWTClaimsSet getJwtClaimsSet() {
        return this.jwtClaimsSet;
    }
}
