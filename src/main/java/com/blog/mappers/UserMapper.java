package com.blog.mappers;

import com.blog.dtos.UserDTO;
import com.blog.forms.UserForm;
import com.blog.models.User;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO userToUserDto(User user);
	User userFormToUser(UserForm userDTO);

}
