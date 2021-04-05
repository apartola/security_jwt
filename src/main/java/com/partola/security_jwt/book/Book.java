package com.partola.security_jwt.book;

import com.partola.security_jwt.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Book extends BaseEntity {
	
	private String name;
	
	private String author;
}
