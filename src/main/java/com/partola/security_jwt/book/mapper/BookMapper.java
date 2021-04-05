package com.partola.security_jwt.book.mapper;

import com.partola.security_jwt.book.Book;
import com.partola.security_jwt.book.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
	
	BookDto convertToDto(Book book);
}
