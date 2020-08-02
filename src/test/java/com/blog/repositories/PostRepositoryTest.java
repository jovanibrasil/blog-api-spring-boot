package com.blog.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

import com.blog.ScenarioFactory;
import com.blog.model.Image;
import com.blog.model.Post;
import com.blog.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	private PageRequest pageSortedByLastUpdateDate = PageRequest.of(0, 5, Sort.by("lastUpdateDate"));
	private PageRequest pageUnsorted = PageRequest.of(0, 5);
	
	@Before
	public void setUp() {
		User user = ScenarioFactory.getUser();
		userRepository.save(user);
		
		Image image = ScenarioFactory.getDecompressedImage();
		imageRepository.save(image);
		
		Post post = ScenarioFactory.getPostJava();
		post.setBanner(image);
		postRepository.save(post);
				
		post = ScenarioFactory.getPostSpring();
		post.setId(null);
		post.setBanner(image);
		postRepository.save(post);
	}
	
	@Test
	public void testFindPostsByTagJava() {
		Page<Post> posts = postRepository.findByCategory("Java", pageSortedByLastUpdateDate);
		assertEquals(2, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByTagSpring() {
		Page<Post> posts = postRepository.findByCategory("Spring", pageSortedByLastUpdateDate);
		assertEquals(1, posts.getNumberOfElements());
	}
	
	@Test
	public void testFindAllPosts() {
		assertEquals(2, postRepository.findAll(pageSortedByLastUpdateDate).getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByUserName() {
		assertEquals(2, postRepository.findByUserName("username", pageSortedByLastUpdateDate).getNumberOfElements());
	}
	
	@Test
	public void testFindPostsByInvalidUserName() {
		assertEquals(0, postRepository.findByUserName("invalidusername", pageSortedByLastUpdateDate).getNumberOfElements());
	}
		
	@Test
	public void testSearchByEspecificTerm() {
		Page<Post> posts = postRepository.findByTerm("Title", pageUnsorted);
		assertEquals(2, posts.getNumberOfElements());
	}
	
	@Test
	public void testDeletePost() {
		Post post = ScenarioFactory.getPostJava();
		Image image = ScenarioFactory.getDecompressedImage();
		imageRepository.save(image);
		post.setId(null);
		post.setBanner(image);
		postRepository.save(post);
		postRepository.delete(post);
		assertFalse(postRepository.findById(post.getId()).isPresent());
	}
	
	@Test
	public void testUpdatePost() {
		Post post = ScenarioFactory.getPostJava();
		Image image = ScenarioFactory.getDecompressedImage();
		imageRepository.save(image);
		post.setId(null);
		post.setBanner(image);
		postRepository.save(post);
		String newBody = "New Body";
		post.setBody(newBody);
		postRepository.save(post);
		assertEquals(newBody, postRepository.findById(post.getId()).get().getBody());
	}
	
}
