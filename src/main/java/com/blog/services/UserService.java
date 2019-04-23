package com.blog.services;

import java.util.List;
import java.util.Optional;

import com.blog.models.User;

public interface UserService {
	List<User> findAll();
	Optional<User> save(User user);
	void deleteByUserName(String userName);
	Optional<User> findByUserName(String userName);
}
