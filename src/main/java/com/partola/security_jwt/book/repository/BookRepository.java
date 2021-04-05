package com.partola.security_jwt.book.repository;

import com.partola.security_jwt.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
