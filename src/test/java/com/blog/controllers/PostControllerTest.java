package com.blog.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.blog.ScenarioFactory;
import com.blog.exception.NotFoundException;
import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.dto.UserDetailsDTO;
import com.blog.model.enums.ProfileTypeEnum;
import com.blog.services.PostService;
import com.blog.services.impl.AuthServiceJwtImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
	private AuthServiceJwtImpl authClient;
	
	private PostDTO postDTO0;
	private PostDTO postDTO1;
	private PostSummaryDTO postSummaryDTO0;
	private PostSummaryDTO postSummaryDTO1;
	
	@Before
	public void setUp() {
		postDTO0 = ScenarioFactory.getPostJavaDTO();
		postDTO1 = ScenarioFactory.getPostDTO1();
		postSummaryDTO0 = ScenarioFactory.getPostSummaryDTO0();
		postSummaryDTO1 = ScenarioFactory.getPostSummaryDTO1();
		when(authClient.checkToken(Mockito.anyString()))
			.thenReturn(new UserDetailsDTO("jovanibrasil", ProfileTypeEnum.ROLE_ADMIN));
	}
		
	/*
	 * GetPost test cases
	 */
	
	@Test
	public void testGetPostValidPostID() throws Exception {
		when(postService.findPostById(1L)).thenReturn(postDTO0);
		mvc.perform(MockMvcRequestBuilders.get("/posts/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", equalTo("Java Title")))
			.andExpect(jsonPath("$.body", equalTo("Java Body")))
			.andExpect(jsonPath("$.summary", equalTo("Java Summary")));
	}
	
	@Test
	public void testGetPostInvalidPostId() throws Exception {
		when(postService.findPostById(10L)).thenThrow(new NotFoundException("error.post.find"));
		mvc.perform(MockMvcRequestBuilders.get("/posts/10"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errors[0].message").value("It was not possible to find the specified post."));
	}
	
	/*
	 * GetPosts test cases
	 */
	
	@Test
	public void testGetPostsPage0() throws Exception {
		when(postService.findPosts(any())).thenReturn(
				new PageImpl<PostDTO>(Arrays.asList(postDTO0)));
		mvc.perform(MockMvcRequestBuilders.get("/posts")
				.param("page", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetPostsPage1() throws Exception {
		when(postService.findPosts(any())).thenReturn(
				new PageImpl<PostDTO>(Arrays.asList(postDTO1)));
		mvc.perform(MockMvcRequestBuilders.get("/posts")
				.param("page", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetPostsPage2() throws Exception {
		when(postService.findPosts(any())).thenReturn(
				new PageImpl<PostDTO>(Arrays.asList()));
		mvc.perform(MockMvcRequestBuilders.get("/posts")
				.param("page", "2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	/*
	 * GetSummaryList test cases
	 */
	@Test
	public void testGetSummaryListJavaTag() throws Exception {
		when(postService.findPostSummaryList(any(), any()))
				.thenReturn(new PageImpl<PostSummaryDTO>(Arrays.asList(postSummaryDTO0)));
		
		mvc.perform(MockMvcRequestBuilders.get("/posts/summaries?category=Java"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetSummaryListScalaTag() throws Exception {
		when(postService.findPostSummaryList(any(), any()))
			.thenReturn(new PageImpl<PostSummaryDTO>(Arrays.asList(postSummaryDTO1)));
		
		mvc.perform(MockMvcRequestBuilders.get("/posts/summaries?category=Scala"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	/*
	 * List posts by user id test cases
	 */
	
	@Test
	public void testGetPostsByUserName() throws Exception {
		PageImpl<PostDTO> page = new PageImpl<PostDTO>(Arrays.asList(postDTO1, postDTO0));
		when(postService.findPostsByUserName(any(), any())).thenReturn(
				page);
		mvc.perform(MockMvcRequestBuilders.get("/posts?username=jovani"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
		
	/*
	 * Update post test cases
	 */
	@Test
	public void testUpdatePost() throws Exception {
		
		MockMultipartFile file = new MockMultipartFile("banner", "orig", null, "file".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("post", "", "application/json",
				asJsonString(postDTO0).getBytes());
		
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.multipart("/posts/" + postDTO0.getId());
		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});
		
		when(postService.update(any(), any(), any())).thenReturn(postDTO0);
		
		mvc.perform(builder.file(file)
				.file(jsonFile)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(postDTO0.getTitle())));
	}
	
	/*
	 * Delete post test cases
	 */
	
	@Test
	public void testDeletePost() throws Exception {
		doNothing().when(postService).deleteByPostId(0L);
		mvc.perform(MockMvcRequestBuilders.delete("/posts/0")
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testDeletePostInvalidPostId() throws Exception {
		doThrow(new NotFoundException("error.post.find")).when(postService).deleteByPostId(Mockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete("/posts/50")
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errors[0].message", equalTo("It was not possible to find the specified post.")));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
}
