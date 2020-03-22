package com.blog.controllers;

import com.blog.enums.ProfileTypeEnum;
import com.blog.exceptions.NotFoundException;
import com.blog.services.impl.JwtAuthenticationProvider;
import com.blog.models.User;
import com.blog.security.TempUser;
import com.blog.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	private JwtAuthenticationProvider authClient;
	
	private User user;
	
	@Before
	public void setUp() {
		this.user = new User();
		this.user.setUserName("jovanibrasil");
		this.user.setFullUserName("Jovani Brasil");
		this.user.setEmail("jovanibrasil@gmail.com");
		this.user.setGithubUserName("jovanibrasil");
		this.user.setLinkedinUserName("jovanibrasil");
		this.user.setPhoneNumber("51999999999");
		this.user.setProfileType(ProfileTypeEnum.ROLE_USER);
		BDDMockito.given(this.authClient.checkToken(Mockito.anyString())).willReturn(new TempUser("jovanibrasil", ProfileTypeEnum.ROLE_USER));
	}
	
	@Test
	public void testFindUserByNameWithInvalidUserName() throws Exception {
		BDDMockito.given(this.userService.findByUserName(Mockito.anyString()))
			.willThrow(new NotFoundException("error.user.find"));
		mvc.perform(MockMvcRequestBuilders.get("/users/sajdoi")
			.header("Authorization", "x.x.x.x")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errors[0].message").value("It was not possible to find the specified user."));
	}
	
	@Test
	public void testFindUserByNameWithValidUserName() throws Exception {
		BDDMockito.given(this.userService.findByUserName("jovanibrasil"))
			.willReturn(user);
		mvc.perform(MockMvcRequestBuilders.get("/users/jovanibrasil")
			.header("Authorization", "x.x.x.x")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.userName", equalTo("jovanibrasil")))
			.andExpect(jsonPath("$.data.fullUserName", equalTo("Jovani Brasil")))
			.andExpect(jsonPath("$.errors").isEmpty());
	}
	
}
