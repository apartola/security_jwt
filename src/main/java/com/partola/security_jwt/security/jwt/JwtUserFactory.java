package com.partola.security_jwt.security.jwt;

import com.partola.security_jwt.model.Role;
import com.partola.security_jwt.model.Status;
import com.partola.security_jwt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
	
	public JwtUserFactory() {
	}
	
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUsername(), user.getLastname(), user.getFirstname(),
				user.getEmail(), user.getPassword(), user.getStatus().equals(Status.ACTIVE), new Date(), mapTo(user.getRoles()));
	}
	
	private static List<GrantedAuthority> mapTo(List<Role> userRole) {
		return userRole.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
}
