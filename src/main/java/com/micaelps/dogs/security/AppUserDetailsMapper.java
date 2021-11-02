package com.micaelps.dogs.security;

import com.micaelps.dogs.user.UserSystem;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;


@Configuration
class AppUserDetailsMapper implements UserDetailsMapper{

	@Override
	public UserDetails map(Object shouldBeASystemUser) {
		return new LoggedUser((UserSystem)shouldBeASystemUser);
	}

}
