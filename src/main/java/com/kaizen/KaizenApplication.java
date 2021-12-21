package com.kaizen;

import com.kaizen.security.jwt.JwtConfiguration;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;


import static com.nimbusds.jose.JWSAlgorithm.RS256;

/**
 * {@code KaizenApplication} is the application.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@SpringBootApplication
public class KaizenApplication {
	/**
	 * Represents the JWT configuration used by the application.
	 */
	@Autowired
	private JwtConfiguration jwtConfiguration;

	/**
	 * The main function to run when starting the application.
	 * 
	 * @param args the inputs recieved with start the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(KaizenApplication.class, args);
	}

	/**
	 * Create a configurable JWT Processor.
	 * 
	 * @throws MalformedURLException if malformed URL has occurred.
	 * @return a configurable JWT Processor
	 */
	@Bean
	public ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor() throws MalformedURLException {
		ResourceRetriever resourceRetriever = new DefaultResourceRetriever(jwtConfiguration.getConnectionTimeout(),
				jwtConfiguration.getReadTimeout());
		URL jwkSetURL = new URL(jwtConfiguration.getJwkUrl());
		JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(jwkSetURL, resourceRetriever);
		ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(RS256, keySource);
		jwtProcessor.setJWSKeySelector(keySelector);
		return jwtProcessor;
	}

}
