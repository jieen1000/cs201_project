package com.kaizen.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * {@code JwtConfiguration} is the JWT configuration for the application.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@Component
@ConfigurationProperties(prefix = "jwt.aws")
public class JwtConfiguration {
    /**
     * Represents the user pool's id of the JWT configuration.
     */
    private String userPoolId;

    /**
     * Represents the region of the JWT configuration.
     */
    private String region;

    /**
     * Represents the connection timeout of the JWT configuration.
     */
    private int connectionTimeout;

    /**
     * Represents the read timeout of the JWT configuration.
     */
    private int readTimeout;

    /**
     * Represents the user name field of the JWT configuration.
     */
    private String userNameField;

    /**
     * Represents the groups field of the JWT configuration.
     */
    private String groupsField;

    /**
     * Represents the admin group of the JWT configuration.
     */
    private String adminGroup;

    /**
     * Represents the http header of the JWT configuration.
     */
    private String httpHeader;

    /**
     * Create a JWT configuration.
     */
    public JwtConfiguration() {
    }

    /**
     * Gets the JWK URL of the JWT configuration.
     * 
     * @return a string representing the JWK URL.
     */
    public String getJwkUrl() {
        return String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", region, userPoolId);
    }

    /**
     * Gets the Cognito Identity Pool URL of the JWT configuration.
     * 
     * @return a string representing the Cognito Identity Pool URL.
     */
    public String getCognitoIdentityPoolUrl() {
        return String.format("https://cognito-idp.%s.amazonaws.com/%s", region, userPoolId);
    }

    /**
     * Gets the user pool's id of the JWT configuration.
     * 
     * @return a string representing the user pool's id.
     */
    public String getUserPoolId() {
        return userPoolId;
    }

    /**
     * Sets the user pool's id of the JWT configuration.
     * 
     * @param userPoolId a string containing the user pool's id.
     */
    public void setUserPoolId(String userPoolId) {
        this.userPoolId = userPoolId;
    }

    /**
     * Gets the connection timeout of the JWT configuration.
     * 
     * @return a int representing the connection timeout.
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets the connection timeout of the JWT configuration.
     * 
     * @param connectionTimeout a int containing the connection timeout.
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Gets the read timeout of the JWT configuration.
     * 
     * @return a int representing the read timeout.
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout of the JWT configuration.
     * 
     * @param readTimeout a int containing the read timeout.
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Gets the region of the JWT configuration.
     * 
     * @return a string representing the region.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of the JWT configuration.
     * 
     * @param region a string containing the region.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the user name field of the JWT configuration.
     * 
     * @return a string representing the user name field.
     */
    public String getUserNameField() {
        return userNameField;
    }

    /**
     * Sets the user name field of the JWT configuration.
     * 
     * @param userNameField a string containing the user name field.
     */
    public void setUserNameField(String userNameField) {
        this.userNameField = userNameField;
    }

    /**
     * Gets the groups field of the JWT configuration.
     * 
     * @return a string representing the groups field.
     */
    public String getGroupsField() {
        return groupsField;
    }

    /**
     * Sets the groups field of the JWT configuration.
     * 
     * @param groupsField a string containing the groups field.
     */
    public void setGroupsField(String groupsField) {
        this.groupsField = groupsField;
    }

    /**
     * Gets the admin group of the JWT configuration.
     * 
     * @return a string representing the admin group.
     */
    public String getAdminGroup() {
        return adminGroup;
    }

    /**
     * Sets the admin group of the JWT configuration.
     * 
     * @param adminGroup a string containing the admin group.
     */
    public void setAdminGroup(String adminGroup) {
        this.adminGroup = adminGroup;
    }

    /**
     * Gets the http header of the JWT configuration.
     * 
     * @return a string representing the http header.
     */
    public String getHttpHeader() {
        return httpHeader;
    }

    /**
     * Sets the http header of the JWT configuration.
     * 
     * @param httpHeader a string containing the http header.
     */
    public void setHttpHeader(String httpHeader) {
        this.httpHeader = httpHeader;
    }
}
