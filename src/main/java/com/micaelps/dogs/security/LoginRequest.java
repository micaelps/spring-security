package com.micaelps.dogs.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

class LoginRequest {

	final String email;
	final String password;

	LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	String getEmail() {
		return email;
	}
	String getPassword() {
		return password;
	}

	UsernamePasswordAuthenticationToken build() {
		return new UsernamePasswordAuthenticationToken(this.email,
				this.password);
	}
}
