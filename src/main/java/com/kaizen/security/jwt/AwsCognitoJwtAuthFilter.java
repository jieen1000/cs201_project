package com.kaizen.security.jwt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * {@code AwsCognitoJwtAuthFilter} is the AWS Cognito JWT authentication filter for the
 * application.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@Component
public class AwsCognitoJwtAuthFilter extends GenericFilter {
    /**
     * A logger to print error message for {@code AwsCognitoJwtAuthFilter}.
     */
    private static final Log logger = LogFactory.getLog(AwsCognitoJwtAuthFilter.class);

    /**
     * Represents the cognito id token processor of the AWS Cognito JWT
     * authentication filter.
     */
    private AwsCognitoIdTokenProcessor cognitoIdTokenProcessor;

    /**
     * Create a AWS Cognito JWT authentication filter with the specific cognito id
     * token processor.
     * 
     * @param cognitoIdTokenProcessor the AWS Cognito JWT authentication filter's cognito id token processor.
     */
    public AwsCognitoJwtAuthFilter(AwsCognitoIdTokenProcessor cognitoIdTokenProcessor) {
        this.cognitoIdTokenProcessor = cognitoIdTokenProcessor;
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a client
     * request for a resource at the end of the chain. The FilterChain passed in to
     * this method allows the Filter to pass on the request and response to the next
     * entity in the chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     *
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = cognitoIdTokenProcessor.authenticate((HttpServletRequest) request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception var6) {
            logger.error("Cognito id Token processing error", var6);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
