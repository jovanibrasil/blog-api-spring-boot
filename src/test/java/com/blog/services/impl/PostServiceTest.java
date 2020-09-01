package com.blog.services.impl;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.ScenarioFactory;
import com.blog.exception.NotFoundException;
import com.blog.model.Image;
import com.blog.model.Post;
import com.blog.model.dto.PostDTO;
import com.blog.model.form.PostForm;
import com.blog.model.mapper.PostMapper;
import com.blog.model.mapper.PostMapperImpl;
import com.blog.repositories.PostRepository;
import com.blog.services.ImageService;
import com.blog.services.PostService;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

	@Autowired
	private PostService postService;
	@MockBean
	private PostRepository postRepository;
	
	@MockBean
	private PostMapper postMapper;

	@MockBean
	private ImageService imageService;
	
	@Before
	public void setUp() {
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("username");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}
	
	@Test
	public void testDeleteExistentPost() {
		when(postRepository.findById(1L)).thenReturn(Optional.of(ScenarioFactory.getPostJava()));
		assertThatCode(() -> postService.deleteByPostId(1L)).doesNotThrowAnyException();
	}
	
	@Test(expected =  NotFoundException.class)
	public void testDeleteInexistentPost() {
		when(postRepository.findById(2L)).thenReturn(Optional.empty());
		postService.deleteByPostId(2L);
	}
	
	@Test
	public void testCreatePost() {
		Post post = ScenarioFactory.getPostJava();
		PostForm postForm = ScenarioFactory.getPostJavaForm();
		Image banner = ScenarioFactory.getDecompressedImage();
				
		when(postMapper.postFormToPost(postForm)).thenReturn(post);
		when(postRepository.findById(1L)).thenReturn(Optional.of(ScenarioFactory.getPostSpring()));
		when(imageService.saveImage(any())).thenAnswer(new Answer<Image>() {
			@Override
			public Image answer(InvocationOnMock invocation) throws Throwable {
				Image banner = invocation.getArgument(0);
				banner.setId(2L);
				return banner;
			}
		});
		when(postRepository.save(any(Post.class))).thenAnswer(new Answer<Post>() {
			@Override
			public Post answer(InvocationOnMock invocation) throws Throwable {
				Post post = invocation.getArgument(0);
				post.setId(2L);
				return post;
			}
		});
		when(postMapper.postToPostDto(post)).thenAnswer(new Answer<PostDTO>() {
			@Override
			public PostDTO answer(InvocationOnMock invocation) throws Throwable {
				Post post = (Post) invocation.getArgument(0);
				return (new PostMapperImpl()).postToPostDto(post);
			}
		});
		
		PostDTO postDTO = postService.create(postForm, banner.getMultipartBanner());
		PostDTO expectedPostDTO = ScenarioFactory.getPostJavaDTO();
		expectedPostDTO.setId(2L);
		expectedPostDTO.setBannerId(post.getBanner().getId());
		expectedPostDTO.setCreationDate(postDTO.getCreationDate());
		expectedPostDTO.setLastUpdateDate(postDTO.getLastUpdateDate());
		assertEquals(expectedPostDTO, postDTO);
	}
	
	@Test
	public void testUpdateExistentPost() {

		Image banner = ScenarioFactory.getDecompressedImage();
		Post receivedPost = ScenarioFactory.getPostJava();
		PostForm postForm = ScenarioFactory.getPostJavaForm();
		
		when(postRepository.findById(1L)).thenReturn(Optional.of(ScenarioFactory.getPostSpring()));
		when(imageService.saveImage(any(Image.class))).thenReturn(banner);

		when(postMapper.postFormToPost(postForm)).thenReturn(receivedPost);
		Post existentPost = ScenarioFactory.getPostSpring();
		when(postRepository.findById(1L)).thenReturn(Optional.of(existentPost));
		
		when(postMapper.postToPostDto(existentPost)).thenAnswer(new Answer<PostDTO>() {
			@Override
			public PostDTO answer(InvocationOnMock invocation) throws Throwable {
				Post post = invocation.getArgument(0);
				return (new PostMapperImpl()).postToPostDto(post);
			}
		});
		
		PostDTO postDTO = postService.update(1L, postForm, banner.getMultipartBanner());
		PostDTO expectedPostDTO = ScenarioFactory.getPostJavaDTO();
		expectedPostDTO.setId(1L);
		expectedPostDTO.setCreationDate(postDTO.getCreationDate());
		expectedPostDTO.setLastUpdateDate(postDTO.getLastUpdateDate());
		assertEquals(expectedPostDTO, postDTO);
	}
	
	@Test
	public void testUpdateExistentPostWithoutSendBanner() {
		Post receivedPost = ScenarioFactory.getPostJava();
		PostForm postForm = ScenarioFactory.getPostJavaForm();
		
		when(postMapper.postFormToPost(postForm)).thenReturn(receivedPost);
		Post existentPost = ScenarioFactory.getPostSpring();
		when(postRepository.findById(1L)).thenReturn(Optional.of(existentPost));
		
		when(postMapper.postToPostDto(existentPost)).thenAnswer(new Answer<PostDTO>() {
			@Override
			public PostDTO answer(InvocationOnMock invocation) throws Throwable {
				Post post = invocation.getArgument(0);
				return (new PostMapperImpl()).postToPostDto(post);
			}
		});
		
		PostDTO postDTO = postService.update(1L, postForm, null);
		PostDTO expectedPostDTO = ScenarioFactory.getPostJavaDTO();
		expectedPostDTO.setId(1L);
		expectedPostDTO.setCreationDate(postDTO.getCreationDate());
		expectedPostDTO.setLastUpdateDate(postDTO.getLastUpdateDate());
		assertEquals(expectedPostDTO, postDTO);
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdateInexistentPost() {
		Post post = ScenarioFactory.getPostJava();
		PostForm postForm = ScenarioFactory.getPostJavaForm();
		
		when(postMapper.postFormToPost(postForm)).thenReturn(post);
		when(postRepository.findById(1L)).thenThrow(NotFoundException.class);
	
		postService.update(1L, postForm, null);	
	}
	

}
