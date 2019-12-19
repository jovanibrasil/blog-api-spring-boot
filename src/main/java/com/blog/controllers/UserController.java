package com.blog.controllers;

import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import com.blog.exceptions.CustomMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dtos.UserDTO;
import com.blog.models.User;
import com.blog.response.Response;
import com.blog.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private UserService userService;
	private CustomMessageSource msgSrc;

	public UserController(UserService userService, CustomMessageSource msgSrc) {
		this.userService = userService;
		this.msgSrc = msgSrc;
	}

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
		
		response.setData(this.userToUserDTO(optUser.get()));
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

		User user = this.userDTOtoUser(userDTO);
		optUser = this.userService.save(user);
		if(!optUser.isPresent()) {
			log.error("It was not possible to create the specified user.");
			response.addError(msgSrc.getMessage("error.user.create"));
			return ResponseEntity.badRequest().body(response);
		}
		log.info("Creating user {}", user.getUserName());
		userDTO = this.userToUserDTO(user);
		response.setData(userDTO);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Convert an User object to an UserDTO object.  
	 * 
	 * @param user that you wish to have converted. 
	 * @return an UserDTO object.
	 */
	private UserDTO userToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUserName());
		userDTO.setFullUserName(user.getFullUserName());
		userDTO.setEmail(user.getEmail());
		userDTO.setGithubUserName(user.getGithubUserName());
		userDTO.setGooglescholarLink(user.getGoogleScholarLink());
		userDTO.setLinkedinUserName(user.getLinkedinUserName());
		userDTO.setPhone(user.getPhoneNumber());
		return userDTO;
	}
	
	/**
	 * Convert an UserDTO object to an User object.
	 * 
	 * @param userDTO is the object that you wish to have converted.
	 * @return an User object.
	 */
	private User userDTOtoUser(UserDTO userDTO) {
		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setEmail(userDTO.getEmail());
		user.setFullUserName(userDTO.getFullUserName());
		user.setGithubUserName(userDTO.getGithubUserName());
		user.setGoogleScholarLink(userDTO.getGooglescholarLink());
		user.setLinkedinUserName(userDTO.getLinkedinUserName());
		user.setPhoneNumber(userDTO.getPhone());
		return user;
	}
	
	
}
