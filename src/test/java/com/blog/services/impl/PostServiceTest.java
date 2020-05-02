package com.blog.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.enums.ProfileTypeEnum;
import com.blog.exceptions.NotFoundException;
import com.blog.models.Image;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.ImageService;
import com.blog.services.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

	@MockBean
	private PostRepository postRepository;
	
	@MockBean
	private ImageService imageService;
	
	@InjectMocks
	private PostServiceImpl postService;
	
	private Post post;
	private User user;
	private List<String> tags;
	
	private MockMultipartFile image;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		postService = new PostServiceImpl(postRepository, imageService);
		
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
		
		image = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		post.setBanner(new Image(image));
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn(user.getUserName());
		SecurityContextHolder.setContext(securityContext);
	}
		
	/*
	 * FindPosts  
	 */
	
	@Test
	public void testFindAllPostsNotNull() {
		when(postRepository.findAll(Mockito.any(PageRequest.class)))
			.thenReturn(new PageImpl<Post>(Arrays.asList(post)));
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		assertNotNull(page); 
	}
	
	@Test
	public void testFindAllPosts() {
		when(postRepository.findAll(Mockito.any(PageRequest.class)))
			.thenReturn(new PageImpl<Post>(Arrays.asList(post)));
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> posts = postService.findPosts(page);
		assertEquals(1, posts.getNumberOfElements()); 
	}
	
	/*
	 * findPostsByCategory 
	 */
	
	@Test
	public void testFindPostsByCategoryValidCategory() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		when(postRepository.findByCategory("Tag", page))
			.thenReturn(new PageImpl<Post>(Arrays.asList(post)));
		Page<Post> posts = postService.findPostsByCategory("Tag", page);
		assertEquals(1, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByCategoryInvalidCategory() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		
		when(postRepository.findByCategory("Java", page))
			.thenReturn(new PageImpl<Post>(new ArrayList<Post>()));
		
		Page<Post> optPost = postService.findPostsByCategory("Java", page);
		assertEquals(0, optPost.getNumberOfElements());
	}
	
	/*
	 * findPostsByUserId
	 */
	
	@Test
	public void testFindPostsByUserIdValidUserId() {
		when(postRepository.findByUserName(Mockito.anyString(), Mockito.any()))
			.thenReturn(new PageImpl<Post>(Arrays.asList(post)));
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> optPost = postService.findPostsByUserName("jovanibrasil", page);
		assertEquals(1, optPost.getNumberOfElements());
	}
		
	@Test
	public void testFindPostsByUserIdInvalidUserId() {
		when(postRepository.findByUserName(Mockito.anyString(), Mockito.any()))
			.thenReturn(new PageImpl<Post>(Arrays.asList()));
		PageRequest page = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
		Page<Post> optPost = postService.findPostsByUserName("jovanibrasil", page);
		assertEquals(0, optPost.getNumberOfElements());
	}
	
	/*
	 * findByPostId
	 */
	
	@Test
	public void testFindPostByPostIdValidPostId() {
		when(postRepository.findById(1L)).thenReturn(Optional.of(post));
		Post post = postService.findPostById(1L);
		assertNotNull(post);
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindPostByPostIdInvalidPostId() {
		when(postRepository.findById(0L)).thenReturn(Optional.empty());
		postService.findPostById(3L);
	}
	
	/*
	 * deleteById
	 */
	
	@Test
	public void testDeletePostByPostIdValidPostId() {
		when(postRepository.findById(0L)).thenReturn(Optional.of(post));
		Post post = postService.deleteByPostId(0L);
		assertNotNull(post);
	}
	
	@Test(expected = NotFoundException.class)
	public void testDeletePostByPostIdInvalidPostId() {
		when(postRepository.findById(3L)).thenReturn(Optional.empty());
		postService.findPostById(3L);
	}
		
	/*
	 * update
	 */

	@Test
	public void testUpdatePostByPostIdValidPostId() {
		post.setId(1L);
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
		String newTitle = "new title";
		post.setTitle(newTitle);
		when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
		Post updatedPost = postService.update(post);
		assertNotNull(updatedPost);
		assertEquals(newTitle, updatedPost.getTitle());
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdatePostInvalidPostId() {
		post.setId(1L);
		PostService postServiceSpy = spy(postService);
		doThrow(NotFoundException.class).when(postServiceSpy).findPostById(post.getId());
		postService.update(post);
	}
	
	/**
	 * Create
	 * 
	 */
	
	@Test
	public void testCreation() {
		when(imageService.saveImage(post.getBanner())).then((InvocationOnMock invocation) -> {
			Image image = (Image) invocation.getArgument(0);
			image.setId(1L);
			return image;
		});
		when(postService.create(post)).then((InvocationOnMock invocation) -> {
			Post post = (Post) invocation.getArgument(0);
			post.setId(2L);
			return post;
		});
		Post updatedPost = postService.create(post);
		assertNotNull(updatedPost);
		assertEquals(2, updatedPost.getId().longValue());
	}
	
	
	
}
