package com.k1ng1nsu.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.k1ng1nsu.filter.AuthoritiesLoggingAfterFilter;
import com.k1ng1nsu.filter.AuthoritiesLoggingAtFilter;
import com.k1ng1nsu.filter.CsrfCookieFilter;
import com.k1ng1nsu.filter.RequestValidationBeforeFilter;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//여기부터
		CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

		http.securityContext(sc -> sc.requireExplicitSave(false)) // security context holder 프레임워크가 맡아라
				// 내가 만든 sessionManagement를 따라서 jsessionId를 만들어줘 첫 로그인 후 그걸로 들어오게해줘
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.csrfTokenRequestHandler(requestAttributeHandler)
						.ignoringRequestMatchers("/contact", "/register")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//여기까지
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)// 순서 중요함
				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests(
						requests -> requests.requestMatchers("/myAccount", "/myLoans", "/myCards").hasRole("USER")
								.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN").requestMatchers("/user")
								.authenticated().requestMatchers("/notices", "/contact", "/register").permitAll())
				.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	// CORS 설정

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200")); // 허용할 출처
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // 허용할 HTTP 메서드
		config.addAllowedHeader("*"); // 허용할 헤더
		config.setAllowCredentials(true); // 모든 인증 허용
		config.setMaxAge(3600L); // cors허용 캐싱시간

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 설정 적용
		return source;
	}
	//

}
