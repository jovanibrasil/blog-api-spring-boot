package com.blog.services;

import com.blog.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
	List<User> findAll();
	Optional<User> save(User user);
	void deleteByUserName(String userName);
	Optional<User> findByUserName(String userName);
}
