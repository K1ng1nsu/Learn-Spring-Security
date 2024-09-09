package com.testtest.oauth.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

	@GetMapping("/")
	public String index(OAuth2AuthenticationToken token) {
		System.out.println(token.getPrincipal());
		token.eraseCredentials();

		System.out.println(token.getPrincipal());
		System.out.println("afdhaejkhgduiqhudhnakslndkjanuiehuvfnovthiortwvaiotvawitvaw");
		return "secure.html";
	}

}
