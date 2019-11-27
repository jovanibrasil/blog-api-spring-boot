package com.blog.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.blog.dtos.DtoUtils;
import com.blog.enums.ProfileTypeEnum;
import com.blog.integrations.AuthClient;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.security.TempUser;
import com.blog.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private AuthClient authClient;
	
	Post post0;
	Post post1;
	
	@Before
	public void setUp() {
		
		User user = new User();
		user.setUserName("jovanibrasil");
		user.setFullUserName("Jovani Brasil");
		user.setEmail("jovanibrasil@gmail.com");
		user.setGithubUserName("jovanibrasil");
		user.setLinkedinUserName("jovanibrasil");
		user.setPhoneNumber("51999999999");
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		
		post0 = new Post();
		post0.setPostId(0L);
		post0.setTitle("Post title");
		post0.setBody("Post body");
		post0.setSummary("Post summary");
		post0.setLastUpdateDate(new Date());
		post0.setCreationDate(new Date());
		post0.setTags(Arrays.asList("Java"));
		post0.setAuthor(user);
		
		post1 = new Post();
		post1.setPostId(0L);
		post1.setTitle("Post2 title");
		post1.setBody("Post2 body");
		post1.setSummary("Post2 summary");
		post1.setLastUpdateDate(new Date());
		post1.setCreationDate(new Date());
		post1.setTags(Arrays.asList("Scala"));
		post1.setAuthor(user);
		
		BDDMockito.given(this.authClient.checkToken(Mockito.anyString()))
			.willReturn(new TempUser("jovanibrasil", ProfileTypeEnum.ROLE_ADMIN));
		
	}
		
	/*
	 * GetPost test cases
	 */
	
	@Test
	public void testGetPostValidPostID() throws Exception {
		BDDMockito.given(this.postService.findPostByPostId(1L)).willReturn(Optional.of(post0));
		mvc.perform(MockMvcRequestBuilders.get("/posts/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.errors").isEmpty())
			.andExpect(jsonPath("$.data.title", equalTo("Post title")))
			.andExpect(jsonPath("$.data.body", equalTo("Post body")))
			.andExpect(jsonPath("$.data.summary", equalTo("Post summary")));
	}
	
	@Test
	public void testGetPostInvalidPostId() throws Exception {
		BDDMockito.given(this.postService.findPostByPostId(10L)).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.get("/posts/10"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].message").value("It was not possible to find the specified post."));
	}
	
	/*
	 * GetPosts test cases
	 */
	
	@Test
	public void testGetPostsPage0() throws Exception {
		BDDMockito.given(this.postService.findPosts(Mockito.any())).willReturn(
				Optional.of(new PageImpl<Post>(Arrays.asList(post0))));
		BDDMockito.given(this.postService.findPosts(PageRequest.of(0, 1,
				Sort.by(Sort.Direction.ASC, "lastUpdateDate"))))
			.willReturn(Optional.of(new PageImpl<Post>(Arrays.asList(post0))));
		
		mvc.perform(MockMvcRequestBuilders.get("/posts?page=0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}


	
	@Test
	public void testGetPostsPage1() throws Exception {
		BDDMockito.given(this.postService.findPosts(Mockito.any())).willReturn(
				Optional.of(new PageImpl<Post>(Arrays.asList(post1))));
		BDDMockito.given(this.postService.findPosts(PageRequest.of(1, 1,
				Sort.by(Sort.Direction.ASC, "lastUpdateDate"))))
			.willReturn(Optional.of(new PageImpl<Post>(Arrays.asList(post1))));
		mvc.perform(MockMvcRequestBuilders.get("/posts").param("page", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void testGetPostsPage2() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/posts").param("page", "2"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty());
	}
	
	/*
	 * GetSummaryList test cases
	 */
	@Test
	public void testGetSummaryListJavaTag() throws Exception {
		BDDMockito.given(this.postService.findPostsByCategory("Java", PageRequest.of(0, 1,
				Sort.by(Sort.Direction.ASC ,"creationDate"))))
				.willReturn(Optional.of(new PageImpl<Post>(Arrays.asList(post0))));

		mvc.perform(MockMvcRequestBuilders.get("/posts/summaries?category=Java"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testGetSummaryListScalaTag() throws Exception {
		BDDMockito.given(this.postService.findPostsByCategory("Scala", PageRequest.of(0, 1,
				Sort.by(Sort.Direction.ASC, "creationDate")))).willReturn(
				Optional.of(new PageImpl<Post>(Arrays.asList(post1))));
		
		mvc.perform(MockMvcRequestBuilders.get("/posts/summaries?category=Scala"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	/*
	 * List top posts test cases
	 */

	@Test
	public void testGetTopPostsInfoList() throws Exception {
		BDDMockito.given(this.postService.findPosts(PageRequest.of(0, 10,
				Sort.by(Sort.Direction.ASC, "lastUpdateDate")))).willReturn(
				Optional.of(new PageImpl<Post>(Arrays.asList(post0))));
		
		mvc.perform(MockMvcRequestBuilders.get("/posts/top"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	/*
	 * List posts by user id test cases
	 */
	
	@Test
	public void testGetPostsByUserName() throws Exception {
		PageImpl<Post> page = new PageImpl<Post>(Arrays.asList(post1, post0));
		BDDMockito.given(this.postService.findPostsByUserName(Mockito.any(), Mockito.any())).willReturn(
				Optional.of(page));
		mvc.perform(MockMvcRequestBuilders.get("/posts/byuser/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	/*
	 * Create post test cases
	 */
	
	@Test
	public void testCreatePost() throws Exception {
		
		MockMultipartFile file = new MockMultipartFile("banner", "orig", null, "file".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("post", "", "application/json",
				asJsonString(DtoUtils.postToPostDTO(post0)).getBytes());
		
		BDDMockito.given(this.postService.create(Mockito.any(), Mockito.any())).willReturn(Optional.of(post0));
		mvc.perform(MockMvcRequestBuilders.multipart("/posts")
				.file(file)
				.file(jsonFile)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	/*
	 * Update post test cases
	 */
	@Test
	public void testUpdatePost() throws Exception {
		
		String newTitle = "Simple test title";
		post0.setTitle(newTitle);

		MockMultipartFile file = new MockMultipartFile("banner", "orig", null, "file".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("post", "", "application/json",
				asJsonString(DtoUtils.postToPostDTO(post0)).getBytes());
		
		BDDMockito.given(this.postService.update(Mockito.any(), Mockito.any()))
			.willReturn(Optional.of(post0));
		
		MockMultipartHttpServletRequestBuilder builder = 
				MockMvcRequestBuilders.multipart("/posts");
		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});
		
		mvc.perform(builder
				.file(file)
				.file(jsonFile)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data.title", equalTo(newTitle)));
	}
	
	/*
	 * Delete post test cases
	 */
	
	@Test
	public void testDeletePost() throws Exception {
		BDDMockito.given(this.postService.deleteByPostId(0L)).willReturn(Optional.of(post0));
		mvc.perform(MockMvcRequestBuilders.delete("/posts/0")
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testDeletePostInvalidPostId() throws Exception {
		BDDMockito.given(this.postService.deleteByPostId(Mockito.anyLong())).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.delete("/posts/50")
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].message", equalTo("It was not possible to find the specified post.")));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
}
