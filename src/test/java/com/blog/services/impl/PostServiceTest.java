package com.blog.services.impl;

import com.blog.enums.ProfileTypeEnum;
import com.blog.exceptions.NotFoundException;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.PostService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostServiceTest {

	@MockBean
	private PostRepository postRepository;
	
	@MockBean
	private FileSystemStorageServiceImpl storage;
	
	@Autowired
	private PostService postService;
	
	private Post post;
	private User user;
	private List<String> tags;
	
	private MultipartFile[] images;
	
	@Before
	public void setUp() {
		tags = new ArrayList<>();
		tags.add("Tag");
		user = new User();
		user.setFullUserName("User Name");
		user.setLastUpdateDate(LocalDateTime.now());
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user.setUserName("jovanibrasil");
		
		post = new Post();
		post.setTitle("Post title");
		post.setBody("Post body");
		post.setSummary("Post summary");
		post.setLastUpdateDate(LocalDateTime.now());
		post.setCreationDate(LocalDateTime.now());
		post.setTags(tags);
		post.setAuthor(user);
		Page<Post> page = new PageImpl<Post>(Arrays.asList(post));
		
		BDDMockito.given(this.postRepository.save(Mockito.any(Post.class))).willReturn(post);
		BDDMockito.given(this.postRepository.findAll(Mockito.any(PageRequest.class))).willReturn(page);
	
		BDDMockito.given(this.postRepository.findById(Mockito.anyLong())).willReturn(Optional.of(post));
		BDDMockito.given(this.postRepository.findByUserName(Mockito.anyString(), Mockito.any()))
			.willReturn(page);
		
		Mockito.doNothing()
			.when(this.storage)
			.saveImage(Mockito.any(), Mockito.any());
		
		Mockito.doNothing()
			.when(this.storage)
			.deletePostDirectory(Mockito.any());
		
		MockMultipartFile image = new MockMultipartFile("data", "filename.txt", 
				"text/plain", "some xml".getBytes());
		this.images = new MockMultipartFile[1];
		this.images[0] = image;
	}
	
	@After
	public void tearDown() {
		this.postRepository.deleteAll();
	}
	
	/*
	 * FindPosts  
	 */
	
	@Test
	public void testFindAllPostsNotNull() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		assertNotNull(page); 
	}
	
	@Test
	public void testFindAllPosts() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = this.postService.findPosts(page);
		assertEquals(1, posts.getNumberOfElements()); 
	}
	
	/*
	 * findPostsByCategory 
	 */
	
	@Test
	public void testFindPostsByCategoryValidCategory() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		BDDMockito.given(this.postRepository.findByCategory("Tag", page))
		.willReturn(new PageImpl<Post>(Arrays.asList(post)));
		Page<Post> posts = this.postService.findPostsByCategory("Tag", page);
		assertEquals(1, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByCategoryInvalidCategory() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		
		BDDMockito.given(this.postRepository.findByCategory("Java", page))
		.willReturn(new PageImpl<Post>(new ArrayList<Post>()));
		
		Page<Post> optPost = this.postService.findPostsByCategory("Java", page);
		assertEquals(0, optPost.getNumberOfElements());
	}
	
	/*
	 * findPostsByUserId
	 */
	
	@Test
	public void testFindPostsByUserIdValidUserId() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> optPost = this.postService.findPostsByUserName("jovanibrasil", page);
		assertEquals(1, optPost.getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByUserIdInvalidUserId() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> optPost = this.postService.findPostsByUserName("jovanibrasil", page);
		assertEquals(1, optPost.getNumberOfElements());
	}
	
	/*
	 * findByPostId
	 */
	
	@Test
	public void testFindPostByPostIdValidPostId() {
		Post post = this.postService.findPostById(1L);
		assertNotNull(post);
	}
	
	@Test
	public void testFindPostByPostIdInvalidPostId() {
		Post post = this.postService.findPostById(3L);
		assertNotNull(post);
	}
	
	/*
	 * deleteById
	 */
	
	@Test
	public void testDeletePostByPostIdValidPostId() {
		BDDMockito.given(this.postRepository.findById(0L)).willReturn(Optional.of(post));
		Post post = this.postService.deleteByPostId(0L);
		assertNotNull(post);
	}
	
	@Test(expected = NotFoundException.class)
	public void testDeletePostByPostIdInvalidPostId() {
		BDDMockito.given(this.postRepository.findById(3L)).willReturn(Optional.empty());
		this.postService.findPostById(3L);
	}
		
	/*
	 * update
	 */
	
	@Test
	public void testUpdatePostByPostIdValidPostId() {
		post.setId(1L);
		post.setTitle("new title");
		Post updatedPost = this.postService.update(post);
		assertNotNull(updatedPost);
		assertEquals("new title", updatedPost.getTitle());
	}
	
	
}
