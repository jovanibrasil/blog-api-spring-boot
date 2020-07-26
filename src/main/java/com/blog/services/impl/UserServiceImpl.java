package com.blog.services.impl;

import com.blog.exception.NotFoundException;
import com.blog.exception.UserException;
import com.blog.model.User;
import com.blog.model.dto.UserDTO;
import com.blog.model.enums.ProfileTypeEnum;
import com.blog.model.form.UserForm;
import com.blog.model.mapper.UserMapper;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public List<UserDTO> findAll() {
		return userRepository.findAll().stream()
				.map(userMapper::userToUserDto)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieve a UserDTO by a given user name.
	 * 
	 * @param userName is the user name to retrieve the UserDTO.
	 */
	@Override
	public UserDTO findByUserName(String userName) {
		return userRepository.findByName(userName)
			.map(userMapper::userToUserDto)
			.orElseThrow(() -> new NotFoundException("error.user.find"));
	}

	/**
	 * Delete an user by name.
	 * 
	 */
	@Override
	public void deleteByUserName(String userName) {
		userRepository.findByName(userName).ifPresentOrElse(
				userRepository::delete, 
				() -> { throw new NotFoundException("error.user.find"); });
	}

	/**
	 * 
	 * Saves a userDTO.
	 * 
	 * @param userDTO is an UserDTO.
	 */
	@Override
	public UserDTO save(UserForm userForm) {
		User user = userMapper.userFormToUser(userForm);
		
		userRepository.findByName(user.getUserName()).ifPresent(
				savedUser -> { throw new UserException("error.user.name.unique"); } );
		
		user.setCreationDate(LocalDateTime.now());
		user.setLastUpdateDate(LocalDateTime.now());
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		return userMapper.userToUserDto(userRepository.save(user));	
	}
	
}