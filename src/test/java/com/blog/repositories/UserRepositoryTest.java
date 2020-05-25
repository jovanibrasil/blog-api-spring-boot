package com.blog.repositories;

import com.blog.model.User;
import com.blog.model.enums.ProfileTypeEnum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
		userRepository.deleteAll();
	}
	
	@Test
	public void testFindUserByName() {
		User user = userRepository.findByName("jovanibrasil").get();
		assertNotNull(user);
	}
	
	@Test
	public void testFindUserByNameInvalidUserName() {
		Optional<User> optUser = userRepository.findByName("jovani");
		assertFalse(optUser.isPresent());
	}
}
