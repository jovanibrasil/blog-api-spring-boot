package blog.presentation.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import blog.business.services.PostService;
import blog.dtos.PostDTO;
import blog.enums.ListOrderType;
import blog.presentation.models.Post;
import blog.presentation.models.User;
import blog.response.Response;

/*
 * 
 * TODO test if the parameters are between the type limits. How to do this before an exception?
 * 
 */

@RestController
@RequestMapping("/posts")
public class PostsController {

	private static final Logger log = LoggerFactory.getLogger(PostsController.class);
	
	@Autowired
	private PostService postService;
	
	/*
	 * Test method. This is a simple ping.
	 * 
	 * 
	 * 
	 */
	@GetMapping("ping/{name}")
	public ResponseEntity<String> ping(@PathVariable("name") String name) {
		return ResponseEntity.ok("Ping was successfully! Hello "+name);
	}
	
	/*
	 * Get post by post id.
	 * 
	 * 
	 * 
	 */
	@GetMapping("/post/{id}")
	public ResponseEntity<Response<PostDTO>> getPost(@PathVariable("id") Long id) {
		
		log.info("Received a blog GET post request!");	
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
		postDTO.setPostId(post.getPostId());
		postDTO.setTitle(post.getTitle());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Create a new post
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<Response<PostDTO>> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
		Response<PostDTO> response = new Response<>();
		
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		//Long userId = Long.parseLong(request.getParameter("userId"));
		User author = new User();
		author.setUserId(postDTO.getUserId());
				
		Post post = new Post(postDTO.getTitle(), postDTO.getBody(), author);
		Optional<Post> optPost = postService.create(post);
		
		if(!optPost.isPresent()) {
			response.getErrors().add("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
			
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setPostId(optPost.get().getPostId());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
	}
	
	/*
	 * Get a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 * 
	 * 
	 * 
	 */
	@RequestMapping("/list/{order}/{length}") 
	public ResponseEntity<Response<ArrayList<PostDTO>>> getPostlist(@PathVariable("order") ListOrderType orderType,  @PathVariable("length") Long length, Model model) { 
		
		Response<ArrayList<PostDTO>> response = new Response<>();
		
		Optional<List<Post>> optLatestPosts = postService.findPosts(orderType, length);
		
		if(!optLatestPosts.isPresent()) {
			response.getErrors().add("It was not possible to create the list of posts.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostDTO> posts = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			PostDTO postDTO = new PostDTO();
			postDTO.setUserId(post.getAuthor().getId());
			postDTO.setBody(post.getBody());
			postDTO.setLastUpdateDate(post.getLastUpdateDate());
			postDTO.setPostId(post.getPostId());
			postDTO.setTitle(post.getTitle());
			posts.add(postDTO);
		});
		
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	/*
	 * Get a list of n latest posts of a specified user.
	 * 
	 * 
	 * 
	 */
	@RequestMapping("/list/{order}/{length}/{userid}") 
	public ResponseEntity<Response<ArrayList<PostDTO>>> getPostListByUser(@PathVariable("order") ListOrderType orderType,
			@PathVariable("length") Long length, @PathVariable("userid") Long userId) { 
		
		Response<ArrayList<PostDTO>> response = new Response<>();
		
		Optional<List<Post>> optLatestPosts = postService.findPostsByUser(orderType, length, userId);
		
		if(!optLatestPosts.isPresent()) {
			response.getErrors().add("It was not possible to create the list of posts.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostDTO> posts = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			PostDTO postDTO = new PostDTO();
			postDTO.setUserId(post.getAuthor().getId());
			postDTO.setBody(post.getBody());
			postDTO.setLastUpdateDate(post.getLastUpdateDate());
			postDTO.setPostId(post.getPostId());
			postDTO.setTitle(post.getTitle());
			posts.add(postDTO);
		});
		
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	/*
	 * Update a specified post.
	 * 
	 * 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ResponseEntity<Response<PostDTO>> updatePost(HttpServletRequest request, @Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
		
		Response<PostDTO> response = new Response<>();
		
		if(bindingResult.hasErrors()) {
			log.error("");
			bindingResult.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Long userId = Long.parseLong(request.getParameter("userId"));
		User author = new User();
		author.setUserId(userId);
				
		Post post = new Post(postDTO.getTitle(), postDTO.getBody(), author);
		Optional<Post> optPost = postService.create(post);
		
		if(!optPost.isPresent()) {
			response.getErrors().add("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
			
		postDTO.setPostId(optPost.get().getPostId());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
		
	}
	
}
