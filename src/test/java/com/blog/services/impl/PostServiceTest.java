package com.blog.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.enums.ProfileTypeEnum;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

	@MockBean
	PostRepository postRepository;
	
	@Autowired
	PostService postService;
	
	@Before
	public void setUp() {
		List<String> tags = new ArrayList<>();
		tags.add("Tag");
		User user = new User();
		user.setFullUserName("User Name");
		user.setLastUpdateDate(new Date());
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user.setUserName("jovanibrasil");
		
		Post post = new Post();
		post.setTitle("Post title");
		post.setBody("Post body");
		post.setSummary("Post summary");
		post.setLastUpdateDate(new Date());
		post.setCreationDate(new Date());
		post.setTags(tags);
		post.setAuthor(user);
		Page<Post> page = new PageImpl<Post>(Arrays.asList(post));
		
		BDDMockito.given(this.postRepository.save(Mockito.any(Post.class))).willReturn(post);
		BDDMockito.given(this.postRepository.findPosts(Mockito.any(PageRequest.class))).willReturn(page);
	
	}
	
	@After
	public void tearDown() {
		this.postRepository.deleteAll();
	}
	
	@Test
	public void testFindAllPostsNotNull() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Optional<Page<Post>> posts = this.postService.findPosts(page);
		assertNotNull(posts.get()); 
	}
	
	@Test
	public void testFindAllPosts() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Optional<Page<Post>> posts = this.postService.findPosts(page);
		assertEquals(1, posts.get().getNumberOfElements()); 
	}
	
}
