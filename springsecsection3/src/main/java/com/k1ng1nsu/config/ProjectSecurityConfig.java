package com.k1ng1nsu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/myAccount", "/myBalance", "/myLoan", "/myCards").authenticated();
			requests.requestMatchers("/notices", "/contact", "/register").permitAll();
		});

		http.formLogin(Customizer.withDefaults()); // 안적으면 허용 안하는거임
		http.httpBasic(Customizer.withDefaults()); // 안적으면 허용 안하는거임
		http.csrf(security -> security.disable());

// none DSL
//      http.authorizeHttpRequests().requestMatchers ~!@!
//		http.formLogin(); // UI 폼 전송 허용
//		http.httpBasic(); // rest Basic Authorization 허용 base64 기반

		return http.build();
	}

	// 메모리 상에 여러 유저를 만드는 법 1
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails admin = User.withDefaultPasswordEncoder()
//				.username("admin")
//				.password("admin")
//				.authorities("admin")
//				.build();
//		
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("user")
//				.authorities("read")
//				.build();
//		
//		return new InMemoryUserDetailsManager(admin,user);
//	}

//	// 메모리 상에 여러 유저를 만드는 법 2
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails admin = User.withUsername("admin").password("admin").authorities("admin").build();
//
//		UserDetails user = User.withUsername("user").password("user").authorities("read").build();
//
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	// 이 패스워드 인코더로 프로젝트 전체를 인코딩함 , 그러나 아래 인코더는 인코딩안함 ->no prod
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
// custom usertable 사용하므로 주석처리
//	@Bean
//	public UserDetailsService userDetailService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

}
