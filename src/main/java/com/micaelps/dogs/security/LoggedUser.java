package com.micaelps.dogs.security;

import com.micaelps.dogs.user.UserSystem;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;


public class LoggedUser implements UserDetails {

	final UserSystem user;
	final User springUserDetails;

	public LoggedUser(UserSystem usuario) {
		this.user = usuario;
		springUserDetails = new User(usuario.getEmail(), usuario.getPassword(), List.of());
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return springUserDetails.getAuthorities();
	}

	public String getPassword() {
		return springUserDetails.getPassword();
	}

	public String getUsername() {
		return springUserDetails.getUsername();
	}

	public boolean isEnabled() {
		return springUserDetails.isEnabled();
	}

	public boolean isAccountNonExpired() {
		return springUserDetails.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return springUserDetails.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return springUserDetails.isCredentialsNonExpired();
	}

	public UserSystem get() {
		return user;
	}
}
