package com.micaelps.dogs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
class TokenManager {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expirationInMillis;

	String generateToken(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		final Date now = new Date();
		final Date expiration = new Date(now.getTime() + this.expirationInMillis);
		
		return Jwts.builder()
			.setIssuer("micaelps")
			.setSubject(user.getUsername())
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS256, this.secret)
			.compact();
	}

	boolean isValid(String jwt) {
		try {
			Jwts.parser()
					.setSigningKey(this.secret)
					.parseClaimsJws(jwt);
			return true;
		} catch (JwtException | IllegalArgumentException e) {			
			return false;
		}  	
	}

	String getUserName(String jwt) {
		return Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(jwt)
				.getBody()
				.getSubject();
	}
}
