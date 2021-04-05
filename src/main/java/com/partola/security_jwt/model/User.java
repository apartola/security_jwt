package com.partola.security_jwt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends BaseEntity {
	
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
	)
	private List<Role> roles;
	
}
