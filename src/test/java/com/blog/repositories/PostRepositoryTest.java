package com.blog.repositories;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.enums.ProfileTypeEnum;
import com.blog.models.Post;
import com.blog.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Before
	public void setUp() {
		List<String> tags = new ArrayList<>();
		tags.add("Tag");
		User user = new User();
		user.setFullUserName("User Name");
		user.setLastUpdateDate(new Date());
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user.setUserName("jovanibrasil");
		user.setEmail("user@gmail.com");
		userRepository.save(user);
		
		Post post = new Post();
		post.setTitle("Post title");
		post.setBody("Post body");
		post.setSummary("Post summary");
		post.setLastUpdateDate(new Date());
		post.setCreationDate(new Date());
		post.setTags(tags);
		post.setAuthor(user);
		this.postRepository.save(post);
		tags.add("Test");
		Post post2 = new Post();
		post2.setTitle("Post2 title");
		post2.setBody("Post2 body");
		post2.setSummary("Post2 summary");
		post2.setLastUpdateDate(new Date());
		post2.setCreationDate(new Date());
		post2.setTags(tags);
		post2.setAuthor(user);
		this.postRepository.save(post2);
	}
	
	@After
	public void tearDown() {
		this.postRepository.deleteAll();
		this.userRepository.deleteAll();
	}
	
	@Test
	public void testFindPostsByUserId() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postRepository.findPostsByUserName("jovanibrasil", page);
		assertEquals("jovanibrasil", posts.getContent().get(0).getAuthor().getUserName());
	}
	
	@Test
	public void testFindPostsByInvalidUserName() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postRepository.findPostsByUserName("jovanibrasil2", page);
		assertEquals(0, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByTagName1() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postRepository.findPostsByCategory("Tag", page);
		assertEquals(2, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByTagName2() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postRepository.findPostsByCategory("Test", page);
		assertEquals(1, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindPosts() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postRepository.findPosts(page);
		assertEquals(2, posts.getNumberOfElements());
	}
	
	@Test
	public void testThirdAddedPost() {
		
		List<String> tags = new ArrayList<>();
		tags.add("Tag");
		User user = new User();
		user.setFullUserName("User Name");
		user.setLastUpdateDate(new Date());
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user.setCreationDate(new Date());
		user.setUserName("jovanibrasil");
		user.setEmail("user@gmail.com");
		userRepository.save(user);
		
		Post post = new Post();
		post.setTitle("Post title");
		post.setBody("Post body");
		post.setSummary("Post summary");
		post.setLastUpdateDate(new Date());
		post.setCreationDate(new Date());
		post.setTags(tags);
		post.setAuthor(user);
		
		post = this.postRepository.save(post);
		
		assertEquals(3L, post.getPostId().longValue());
	}
	
}
