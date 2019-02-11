package blog.presentation.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import blog.business.services.PostService;
import blog.dtos.PostDTO;
import blog.dtos.PostInfo;
import blog.dtos.SummaryDTO;
import blog.presentation.models.Post;
import blog.presentation.models.User;
import blog.response.Response;

/*
 * 
 * TODO test if the parameters are between the type limits. How to do this before an exception?
 * 
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
public class PostsController {

	private static final Logger log = LoggerFactory.getLogger(PostsController.class);
	
	@Autowired
	private PostService postService;
	
	/*
	 * Test method. This is a simple ping.
	 * 
	 * @param name is an user name passed by url parameter.
	 * 
	 */
	@GetMapping("ping/{name}")
	public ResponseEntity<String> ping(@PathVariable("name") String name) {
		return ResponseEntity.ok("Ping was successfully! Hello "+name);
	}
	
	/*
	 * Get post by post id.
	 * 
	 * @param id is the post id. 
	 * 
	 */
	@GetMapping("/post/{id}")
	public ResponseEntity<Response<PostDTO>> getPost(@PathVariable("id") Long id) {
		
		Response<PostDTO> response = new Response<>();
		Optional<Post> optPost = postService.findById(id);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to find the specified post.");
			response.getErrors().add("It was not possible to find the specified post.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = optPost.get(); 
		PostDTO postDTO = new PostDTO();
		postDTO.setUserId(post.getAuthor().getId());
		postDTO.setBody(post.getBody());
		postDTO.setLastUpdateDate(post.getLastUpdateDate());
		postDTO.setId(post.getPostId());
		postDTO.setTitle(post.getTitle());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Create a new post
	 * 
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<Response<PostDTO>> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
		
		Response<PostDTO> response = new Response<>();
		
		if(bindingResult.hasErrors()) {
			log.error("It was not possible to create the specified post.");
			bindingResult.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		User author = new User();
		author.setUserId(postDTO.getUserId());
				
		Post post = new Post(postDTO.getTitle(), postDTO.getSummary(), postDTO.getBody(), author);
		Optional<Post> optPost = postService.create(post);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to create the specified post.");
			response.getErrors().add("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
			
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setId(optPost.get().getPostId());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Update a specified post.
	 * 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ResponseEntity<Response<PostDTO>> updatePost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
		
		Response<PostDTO> response = new Response<>();
		
		if(bindingResult.hasErrors()) {
			log.error("It was not possible to update the specified post. Invalid data.");
			bindingResult.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		User author = new User();
		author.setUserId(postDTO.getUserId());
				
		Post post = new Post(postDTO.getTitle(), postDTO.getSummary(), postDTO.getBody(), author);
		post.setPostId(postDTO.getId());
		post.setLastUpdateDate(new Date());
		Optional<Post> optPost = postService.create(post);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to update the specified post. Internal error.");
			response.getErrors().add("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
			
		postDTO.setId(optPost.get().getPostId());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
		
	}
	
	/*
	 * Get a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 * 
	 * @param length is the size of the post list that will be returned.
	 * 
	 */
	@RequestMapping("/list/{length}") 
	public ResponseEntity<Response<ArrayList<PostDTO>>> getPostlist(@PathVariable("length") Long length, Model model) { 
		
		Response<ArrayList<PostDTO>> response = new Response<>();
		
		Optional<List<Post>> optLatestPosts = postService.findPosts(length);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of posts.");
			response.getErrors().add("It was not possible to create the list of posts.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostDTO> posts = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			System.out.println(post);
			PostDTO postDTO = new PostDTO();
			postDTO.setUserId(post.getAuthor().getId());
			postDTO.setSummary(post.getSummary());
			postDTO.setBody(post.getBody());
			postDTO.setLastUpdateDate(post.getLastUpdateDate());
			postDTO.setId(post.getPostId());
			postDTO.setTitle(post.getTitle());
			posts.add(postDTO);
		});
		
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/summaries/{length}") 
	public ResponseEntity<Response<ArrayList<SummaryDTO>>> getSummarylist(@PathVariable("length") Long length, Model model) { 
		
		Response<ArrayList<SummaryDTO>> response = new Response<>();
		
		Optional<List<Post>> optLatestPosts = postService.findPosts(length);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of summaries.");
			response.getErrors().add("It was not possible to create the list of summaries.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<SummaryDTO> summaries = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			SummaryDTO summaryDTO = new SummaryDTO(post.getPostId(), post.getTitle(), 
					post.getLastUpdateDate(), post.getSummary(), post.getAuthor().getUsername());
			summaries.add(summaryDTO);
		});
		
		response.setData(summaries);
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/top/{length}") 
	public ResponseEntity<Response<ArrayList<PostInfo>>> getTopPostsInfoList(@PathVariable("length") Long length, Model model) { 
		
		log.info("Getting a list of post information (title + id)");
		
		Response<ArrayList<PostInfo>> response = new Response<>();
		
		Optional<List<Post>> optLatestPosts = postService.findPosts(length);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of info list.");
			response.getErrors().add("It was not possible to create the list of info list.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostInfo> postInfoList = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			PostInfo summaryDTO = new PostInfo(post.getPostId(), post.getTitle());
			postInfoList.add(summaryDTO);
		});
		
		response.setData(postInfoList);
		return ResponseEntity.ok(response);
		
	}
	
	/*
	 * Get a list of n latest posts of a specified user.
	 * 
	 * @param length is the size of the post list that will be returned.
	 * @param userId is the user identification. 
	 * 
	 */
	@RequestMapping("/list/{length}/{userid}") 
	public ResponseEntity<Response<ArrayList<PostDTO>>> getPostListByUser(@PathVariable("length") Long length, @PathVariable("userid") Long userId) { 
		
		Response<ArrayList<PostDTO>> response = new Response<>();
		Optional<List<Post>> optLatestPosts = postService.findPostsByUser(length, userId);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of posts.");
			response.getErrors().add("It was not possible to create the list of posts.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostDTO> posts = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			PostDTO postDTO = new PostDTO();
			postDTO.setUserId(post.getAuthor().getId());
			postDTO.setBody(post.getBody());
			postDTO.setSummary(post.getSummary());
			postDTO.setLastUpdateDate(post.getLastUpdateDate());
			postDTO.setId(post.getPostId());
			postDTO.setTitle(post.getTitle());
			posts.add(postDTO);
		});
		
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	/*
	 * Delete a specific post.
	 * 
	 */
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<Response<PostDTO>> deletePost(@PathVariable("id") Long id){
		Response<PostDTO> response = new Response<>();
		
		Optional<Post> optPost = postService.deleteById(id);
		
		if(!optPost.isPresent()) {
			response.getErrors().add("It was not possible to delete the post.");
		}
		
		Post post = optPost.get(); 
		PostDTO postDTO = new PostDTO();
		postDTO.setUserId(post.getAuthor().getId());
		postDTO.setBody(post.getBody());
		postDTO.setLastUpdateDate(post.getLastUpdateDate());
		postDTO.setId(post.getPostId());
		postDTO.setTitle(post.getTitle());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
	}
	
}
