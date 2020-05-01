package com.blog.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.dtos.UserDTO;
import com.blog.forms.UserForm;
import com.blog.mappers.UserMapper;
import com.blog.models.User;
import com.blog.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor 
@Slf4j
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	/**
	 * Retrieve a UserDTO by a given user name.
	 * 
	 * @param userName is the user name to retrieve the UserDTO.
	 * @return A response object with the UserDTO and empty error message on success, and data fiend empty and a list 
	 * of error messages on failure.  
	 */
	@GetMapping("/{userName}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userName") String userName){
		User user = userService.findByUserName(userName);
		UserDTO userDto = userMapper.userToUserDto(user);
		return ResponseEntity.ok(userDto);
	}
	
	/**
	 * Delete an user by name.
	 * 
	 */
	@DeleteMapping("/{userName}")
	public ResponseEntity<?> deleteUser(@PathVariable("userName") String userName){
		userService.deleteByUserName(userName);
 		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 
	 * Saves a userDTO.
	 * 
	 * @param userDTO is an UserDTO.
	 * @return An Response object with the saved UserDTO and empty error message list on success, and an Response object 
	 * with empty data and error messages on failure.  
	 */
	@PostMapping
	public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserForm userForm, UriComponentsBuilder uriBuilder){
		User user = userMapper.userFormToUser(userForm);
		log.info("Creating user {}", user.getUserName());
		user = userService.save(user);
		UserDTO userDto = userMapper.userToUserDto(user);
		URI uri = uriBuilder.path("/users/{userName}")
				.buildAndExpand(userDto.getUserName())
				.toUri();
		return ResponseEntity
				.created(uri)
				.body(userDto);
	}

}
