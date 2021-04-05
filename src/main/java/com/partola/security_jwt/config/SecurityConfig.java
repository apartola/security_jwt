package com.partola.security_jwt.config;

import com.partola.security_jwt.security.jwt.JwtConfigurer;
import com.partola.security_jwt.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private final String ADMIN_ENDPOINTS = "/api/admin";
	private final String LOGIN_ENDPOINTS = "/api/auth/login";
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(LOGIN_ENDPOINTS).permitAll()
				.antMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.apply(new JwtConfigurer(jwtTokenProvider));
		
	}
}
