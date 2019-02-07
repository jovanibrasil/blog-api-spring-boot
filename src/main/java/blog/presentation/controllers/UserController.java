package blog.presentation.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.business.services.UserService;
import blog.dtos.UserDTO;
import blog.presentation.models.User;
import blog.response.Response;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<UserDTO>> getUser(@PathVariable("id") Long id){
		
		Response<UserDTO> response = new Response<>();
		
		Optional<User> optUser = this.userService.findById(id);
		
		if(!optUser.isPresent()) {
			
		}
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("teste");
		userDTO.setGitHubUserName("github");
		userDTO.setLinkedInUserName("linkedin");
		userDTO.setFullName("fullname");
		userDTO.setGoogleScholarLink("google");
		userDTO.setLattesLink("lattes");
		userDTO.setLinkedInUserName("username");
		userDTO.setPhone("phone");
		userDTO.setUserName("username");
		userDTO.setId(optUser.get().getId());
		
		response.setData(userDTO);
		return ResponseEntity.ok(response);
	}
	
}
