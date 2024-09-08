//package com.k1ng1nsu.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.k1ng1nsu.model.Customer;
//import com.k1ng1nsu.repository.CustomerRepository;
//
//@Service
//public class EazyBankUserDetails implements UserDetailsService {
//
//	// daoAuthenticationProvider가 UserDetailService를 구현한 이 클래스를 찾아서 인증요청 할 것
//	// 현재는 ProjectSecurityConfig에서 UserDetailService 를 리턴하는 빈이 있어서 2개라서 오류남
//	// Primary 붙이면 안날듯? 나네 ProjectSecurityConfig에서 UserDetailService 를 리턴하는 빈 주석처리
//	private CustomerRepository customerRepository;
//
//	public EazyBankUserDetails(CustomerRepository customerRepository) {
//		super();
//		this.customerRepository = customerRepository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		String userName, password = null;
//		List<GrantedAuthority> authorities = null;
//		List<Customer> customer = customerRepository.findByEmail(username);
//		if (customer.size() == 0) {
//			// no user
//			throw new UsernameNotFoundException("User details not found for the user: " + username);
//		} else {
//			// user exist
//			userName = customer.get(0).getEmail();
//			password = customer.get(0).getPwd();
//			authorities = new ArrayList<>();
//			authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
//
//		}
//		return new User(username, password, authorities);
//
//	}
//
//}
