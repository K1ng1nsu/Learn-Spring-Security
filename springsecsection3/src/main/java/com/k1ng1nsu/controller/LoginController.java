package com.k1ng1nsu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.k1ng1nsu.model.Customer;
import com.k1ng1nsu.repository.CustomerRepository;

@RestController
public class LoginController {

	private CustomerRepository customerRepository;

	public LoginController(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		Customer savedCustomer = null;
		ResponseEntity response = null;
		try {
			savedCustomer = customerRepository.save(customer);
			if (savedCustomer.getId() > 0) {
				response = ResponseEntity.status(HttpStatus.CREATED)
						.body("Given user details are successfully registerd");
			}

		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured due to" + e.getMessage());

		}

		return response;
	}

}
