package com.micaelps.dogs.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

class JwtAuthenticationFilter extends OncePerRequestFilter {

	final TokenManager tokenManager;
	final UsersService usersService;
	
	JwtAuthenticationFilter(TokenManager tokenManager, UsersService usersService) {
		this.tokenManager = tokenManager;
		this.usersService = usersService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final Optional<String> possibleToken = getTokenFromRequest(request);
        if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) {

        	final String userName = tokenManager.getUserName(possibleToken.get());
            final UserDetails userDetails = usersService.loadUserByUsername(userName);
            final UsernamePasswordAuthenticationToken authentication =
            			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder
					.getContext()
					.setAuthentication(authentication);
        }
        chain.doFilter(request, response);
	}

	private Optional<String> getTokenFromRequest(HttpServletRequest request) {
		final String authToken = request.getHeader("Authorization");
		return Optional.ofNullable(authToken).map(x->x.split(" ")[1]);
	}
}
