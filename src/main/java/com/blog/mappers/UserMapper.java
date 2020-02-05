package com.blog.mappers;

import com.blog.dtos.UserDTO;
import com.blog.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO userToUserDto(User user);
    User userDtoToUser(UserDTO userDTO);

}
