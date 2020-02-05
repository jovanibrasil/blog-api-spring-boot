package com.blog.controllers;

import com.blog.dtos.UserDTO;
import com.blog.exceptions.CustomMessageSource;
import com.blog.mappers.UserMapper;
import com.blog.models.User;
import com.blog.response.Response;
import com.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor @Slf4j
public class UserController {

	private final UserService userService;
	private final CustomMessageSource msgSrc;
	private final UserMapper userMapper;

	/**
	 * Retrieve a UserDTO by a given user name.
	 * 
	 * @param userName is the user name to retrieve the UserDTO.
	 * @return A response object with the UserDTO and empty error message on success, and data fiend empty and a list 
	 * of error messages on failure.  
	 */
	@GetMapping("/{userName}")
	public ResponseEntity<Response<UserDTO>> getUser(@PathVariable("userName") String userName){
		
		Response<UserDTO> response = new Response<>();
		Optional<User> optUser = this.userService.findByUserName(userName);
		
		if(!optUser.isPresent()) {
			log.error("It was not possible to find the specified user.");
			response.addError(msgSrc.getMessage("error.user.find"));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(userMapper.userToUserDto(optUser.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Delete an user by name.
	 * 
	 */
	@DeleteMapping("/{userName}")
	public ResponseEntity<Response<String>> deleteUser(@PathVariable("userName") String userName){
		Response<String> response = new Response<String>();
		this.userService.deleteByUserName(userName);
 		return ResponseEntity.ok(response);
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
	public ResponseEntity<Response<UserDTO>> saveUser(@Valid @RequestBody UserDTO userDTO){
		Response<UserDTO> response = new Response<UserDTO>();

		Optional<User> optUser = userService.findByUserName(userDTO.getUserName());
		if(optUser.isPresent()){
			log.error("It was not possible to create the specified user. Username already exists.");
			response.addError(msgSrc.getMessage("error.user.name.unique"));
			return ResponseEntity.badRequest().body(response);
		}

		User user = userMapper.userDtoToUser(userDTO);
		optUser = this.userService.save(user);
		if(!optUser.isPresent()) {
			log.error("It was not possible to create the specified user.");
			response.addError(msgSrc.getMessage("error.user.create"));
			return ResponseEntity.badRequest().body(response);
		}
		log.info("Creating user {}", user.getUserName());
		userDTO = userMapper.userToUserDto(user);
		response.setData(userDTO);
		return ResponseEntity.ok(response);
	}

}
