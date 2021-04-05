package com.partola.security_jwt.service;

import com.partola.security_jwt.model.Role;
import com.partola.security_jwt.model.Status;
import com.partola.security_jwt.model.User;
import com.partola.security_jwt.repository.RoleRepository;
import com.partola.security_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User register(User user) {
		Role roleUser = roleRepository.findByName("ROLE_USER");
		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleUser);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);
		user.setStatus(Status.ACTIVE);
		User registered = userRepository.save(user);
		return registered;
	}
	
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}
	
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
}
