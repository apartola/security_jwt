package com.partola.security_jwt.security.jwt;

import com.partola.security_jwt.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
	
	@Value("${jwt.token.secret}")
	private String secret;
	
	@Value("${jwt.token.expired}")
	private Long validityInMilliseconds;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String createToken(String userName, List<Role> roles) {
		final Claims claims = Jwts.claims().setSubject(userName);
		claims.put("roles", getRoleNames(roles));
		
		final Date now = new Date();
		final Date validity = new Date(now.getTime() + validityInMilliseconds);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public Authentication getAuthentication(String token) {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		final String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			final long time = new Date().getTime() / 1000;
			final boolean before = claimsJws.getBody().getExpiration().getTime() > time;
			return before;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtAuthenticationException("oooh");
		}
	}
	
	public List<String> getRoleNames(List<Role> roles) {
		return roles.stream()
				.map(Role::getName)
				.collect(Collectors.toList());
	}
}
