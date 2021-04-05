package com.partola.security_jwt.security;

import com.partola.security_jwt.model.User;
import com.partola.security_jwt.security.jwt.JwtUser;
import com.partola.security_jwt.security.jwt.JwtUserFactory;
import com.partola.security_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		final JwtUser jwtUser = JwtUserFactory.create(user);
		
		return jwtUser;
	}
}
