package com.partola.security_jwt.book.service;

import com.partola.security_jwt.book.Book;
import com.partola.security_jwt.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DefaultBookService implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}
}
