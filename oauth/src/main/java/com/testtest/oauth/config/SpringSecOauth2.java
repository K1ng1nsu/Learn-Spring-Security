package com.testtest.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecOauth2 {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeRequests().anyRequest().authenticated().and().oauth2Login();

		return http.build();
	}

//	@Bean
//	public ClientRegistrationRepository clientRepository() {
//		ClientRegistration clientReg = clientRegistration();
//		return new InMemoryClientRegistrationRepository(clientReg);
//	}
//
//	private ClientRegistration clientRegistration() {
//		return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("d")
//				.clientSecret("d").build();
//	}

}
