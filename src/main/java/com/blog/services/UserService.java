package com.blog.services;

import java.util.List;

import com.blog.model.dto.UserDTO;
import com.blog.model.form.UserForm;

public interface UserService {
	List<UserDTO> findAll();
	UserDTO save(UserForm userForm);
	void deleteByUserName(String userName);
	UserDTO findByUserName(String userName);
}
