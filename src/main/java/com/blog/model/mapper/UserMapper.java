package com.blog.model.mapper;

import org.mapstruct.Mapper;

import com.blog.model.User;
import com.blog.model.dto.UserDTO;
import com.blog.model.form.UserForm;

@Mapper
public interface UserMapper {

    UserDTO userToUserDto(User user);
	User userFormToUser(UserForm userDTO);

}
