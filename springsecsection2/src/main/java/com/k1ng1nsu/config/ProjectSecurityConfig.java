package com.k1ng1nsu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/myAccount", "/myBalance", "/myLoan", "/myCards").authenticated();
			requests.requestMatchers("/notices", "/contact").permitAll();
		});

		http.formLogin(Customizer.withDefaults()); // 안적으면 허용 안하는거임
		http.httpBasic(Customizer.withDefaults()); // 안적으면 허용 안하는거임

// none DSL
//      http.authorizeHttpRequests().requestMatchers ~!@!
//		http.formLogin(); // UI 폼 전송 허용
//		http.httpBasic(); // rest Basic Authorization 허용 base64 기반

		return http.build();
	}

}
