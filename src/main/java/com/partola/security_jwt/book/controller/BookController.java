package com.partola.security_jwt.book.controller;

import com.partola.security_jwt.book.Book;
import com.partola.security_jwt.book.dto.BookDto;
import com.partola.security_jwt.book.mapper.BookMapper;
import com.partola.security_jwt.book.service.BookService;
import com.partola.security_jwt.model.User;
import com.partola.security_jwt.security.jwt.JwtTokenProvider;
import com.partola.security_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/auth/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequestDto requesrDto) {
//		try {
		final String username = requesrDto.getUsername();
		final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requesrDto.getPassword()));
		
		final User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		final String token = jwtTokenProvider.createToken(username, user.getRoles());
		
		final AuthenticationResponse authenticationResponse = new AuthenticationResponse(username, token);
		return ResponseEntity.ok(authenticationResponse);
//		} catch (AuthenticationException e) {
//			throw new BadCredentialsException("Invalid username");
//		}
	}
	
	@GetMapping("/")
	public List<BookDto> getBooks() {
		final List<Book> books = bookService.getBooks();
		final List<BookDto> result = new LinkedList<>();
		books.forEach(book -> result.add(bookMapper.convertToDto(book)));
		return result;
	}
}
