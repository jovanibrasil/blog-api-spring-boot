package com.blog.repositories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.ScenarioFactory;
import com.blog.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	private User user;
	
	@Before
	public void setUp() {
		user = ScenarioFactory.getUser();
		userRepository.save(user);
	}
	
	@Test
	public void testFindUserByName() {
		assertTrue(userRepository.findByName(user.getUserName()).isPresent());
	}
	
	@Test
	public void testFindUserByNameInvalidUserName() {
		assertFalse(userRepository.findByName("invalid_name").isPresent());
	}
	
}
