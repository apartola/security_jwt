package com.partola.security_jwt.repository;

import com.partola.security_jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
