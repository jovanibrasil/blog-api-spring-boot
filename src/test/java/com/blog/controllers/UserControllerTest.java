package com.blog.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blog.ScenarioFactory;
import com.blog.exception.NotFoundException;
import com.blog.model.dto.UserDTO;
import com.blog.model.dto.UserDetailsDTO;
import com.blog.model.enums.ProfileTypeEnum;
import com.blog.services.UserService;
import com.blog.services.impl.AuthServiceJwtImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AuthServiceJwtImpl authClient;
	
	private UserDTO userDTO;
	
	@Before
	public void setUp() {
		userDTO = ScenarioFactory.getUserDTO();
		when(authClient.checkToken(Mockito.anyString()))
			.thenReturn(new UserDetailsDTO("jovanibrasil", ProfileTypeEnum.ROLE_USER));
	}
	
	@Test
	public void testFindUserByNameWithInvalidUserName() throws Exception {
		when(userService.findByUserName(Mockito.anyString()))
			.thenThrow(new NotFoundException("error.user.find"));
		mvc.perform(MockMvcRequestBuilders.get("/users/sajdoi")
			.header("Authorization", "x.x.x.x")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value("It was not possible to find the specified user."));
	}
	
	@Test
	public void testFindUserByNameWithValidUserName() throws Exception {
		when(userService.findByUserName("jovanibrasil")).thenReturn(userDTO);
		mvc.perform(MockMvcRequestBuilders.get("/users/jovanibrasil")
			.header("Authorization", "x.x.x.x")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userName", equalTo("jovanibrasil")))
			.andExpect(jsonPath("$.fullUserName", equalTo("Jovani Brasil")));
	}
	
}
