package com.blog.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.enums.ProfileTypeEnum;
import com.blog.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Before
	public void setUp() {
		List<String> tags = new ArrayList<>();
		tags.add("Tag");
		User user = new User();
		user.setFullUserName("User Name");
		user.setLastUpdateDate(LocalDateTime.now());
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user.setUserName("jovanibrasil");
		user.setEmail("user@gmail.com");
		userRepository.save(user);
	}
	
	@After
	public void tearDown() {
		this.userRepository.deleteAll();
	}
	
	@Test
	public void testFindUserByName() {
		User user = this.userRepository.findUserByName("jovanibrasil");
		assertNotNull(user);
	}
	
	@Test
	public void testFindUserByNameInvalidUserName() {
		User user = this.userRepository.findUserByName("jovani");
		assertNull(user);
	}
}
