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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.enums.ProfileTypeEnum;
import com.blog.models.Post;
import com.blog.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
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
		//user.setPosts(new HashSet<Post>());
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
	}
	
	@After
	public void tearDown() {
		this.postRepository.deleteAll();
	}
	
	@Test
	public void testFindPostByName() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postRepository.findPostsByUserId("jovanibrasil", page);
		System.out.println(posts.getContent().get(0).toString());
		assertEquals("jovanibrasil", posts.getContent().get(0).getAuthor().getUsername());
	}
	
}
