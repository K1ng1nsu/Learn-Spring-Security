package com.k1ng1nsu.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.k1ng1nsu.model.Customer;
import com.k1ng1nsu.repository.CustomerRepository;

@Component
public class EazyBankUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;

	public EazyBankUsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder,
			CustomerRepository customerRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwq = authentication.getCredentials().toString();
		List<Customer> customer = customerRepository.findByEmail(username);
		if (customer.size() > 0) {
			if (passwordEncoder.matches(pwq, customer.get(0).getPwd())) {
				// 통과
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
				return new UsernamePasswordAuthenticationToken(username, pwq, authorities);
			} else {
				// 통과 x
				throw new BadCredentialsException("Invaild password");
			}
		} else {
			throw new BadCredentialsException("No user registered with this details!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
