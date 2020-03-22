package com.blog.services;

import com.blog.models.User;

import java.util.List;

public interface UserService {
	List<User> findAll();
	User save(User user);
	void deleteByUserName(String userName);
	User findByUserName(String userName);
}
