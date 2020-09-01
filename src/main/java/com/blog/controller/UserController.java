package com.blog.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.dto.UserDTO;
import com.blog.model.form.UserForm;
import com.blog.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor 
public class UserController {

	private final UserService userService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{userName}")
	public UserDTO getUser(@PathVariable("userName") String userName){
		return userService.findByUserName(userName);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{userName}")
	public void deleteUser(@PathVariable("userName") String userName){
		log.info("Deleting user {}", userName);
		userService.deleteByUserName(userName);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Void> saveUser(@Valid @RequestBody UserForm userForm, UriComponentsBuilder uriBuilder){
		log.info("Creating user {}", userForm.getUserName());
		UserDTO userDto = userService.save(userForm);
		  
		URI uri = uriBuilder.path("/users/{userName}")
				.buildAndExpand(userDto.getUserName())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
