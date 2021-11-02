package com.micaelps.dogs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prod", "test"})
public class UserAuthenticationController {

	final AuthenticationManager authManager;
	final TokenManager tokenManager;
	private static final Logger log = LoggerFactory.getLogger(UserAuthenticationController.class);

	UserAuthenticationController(AuthenticationManager authManager, TokenManager tokenManager) {
		this.authManager = authManager;
		this.tokenManager = tokenManager;
	}

	@PostMapping
	ResponseEntity<Map<String, String>> authenticate(@RequestBody LoginRequest loginInfo) {
		UsernamePasswordAuthenticationToken authenticationToken = loginInfo.build();
		try {
			final Authentication authentication = authManager.authenticate(authenticationToken);
			final String jwt = tokenManager.generateToken(authentication);
			return ResponseEntity.ok(Map.of("token",jwt));
		
		} catch (AuthenticationException e) {
			log.error("[Autenticacao] {}",e);
			return ResponseEntity.badRequest().build();
		}
		
	}
}
