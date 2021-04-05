package com.partola.security_jwt.repository;

import com.partola.security_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String name);
}
