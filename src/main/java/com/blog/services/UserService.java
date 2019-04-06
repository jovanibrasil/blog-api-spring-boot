package com.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.blog.models.User;

public interface UserService extends UserDetailsService {
	boolean authenticate(String username, String password);
	List<User> findAll();
	Optional<User> findById(Long id);
	User create(User user);
	void deleteById(Long id);
	void saveUser(String userName, String fullUserName, String Password);
}
