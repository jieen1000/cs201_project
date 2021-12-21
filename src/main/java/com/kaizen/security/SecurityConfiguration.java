package com.kaizen.security;

import com.kaizen.security.jwt.AwsCognitoJwtAuthFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * {@code SecurityConfiguration} is the security configuration for the
 * application.
 *
 * @author Teo Keng Swee
 * @author Tan Jie En
 * @author Chong Zhan Han
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-29
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	/**
	 * The AWS Cognito JWT Authentication Filter used by the application.
	 */
	@Autowired
	private AwsCognitoJwtAuthFilter awsCognitoJwtAuthenticationFilter;

	/**
	 * Override the parent method to configure the {@link HttpSecurity}. Add
	 * {@code awsCognitoJwtAuthenticationFilter} before
	 * {@link UsernamePasswordAuthenticationFilter} and restricted all api
	 * endpoints.
	 *
	 * Any endpoint that requires defense against common vulnerabilities can be
	 * specified here, including public ones. See
	 * {@link HttpSecurity#authorizeRequests} and the `permitAll()` authorization
	 * rule for more details on public endpoints.
	 * 
	 * @param http the {@link HttpSecurity} to modify
	 * @throws Exception if an error occurs
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().cacheControl();
		http.csrf().disable().authorizeRequests().antMatchers("/api/companies/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/employees/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/employeeSkills/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/skills/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/covidTest/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/dashboard/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/transactions/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/api/projects/**")
				.hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString()).antMatchers("/swagger-ui/**")
				.permitAll().antMatchers("/v2/api-docs").permitAll().antMatchers("/swagger-ui/index.html").permitAll()
				.anyRequest().authenticated().and()
				.addFilterBefore(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	private static final String[] AUTH_WHITELIST = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
			"/webjars/**", "/static/**", "/" };

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(AUTH_WHITELIST);
	}

}