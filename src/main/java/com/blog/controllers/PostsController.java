package com.blog.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dtos.DtoUtils;
import com.blog.dtos.PostDTO;
import com.blog.dtos.PostInfo;
import com.blog.dtos.SummaryDTO;
import com.blog.models.Post;
import com.blog.response.Response;
import com.blog.services.PostService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
public class PostsController {

	private static final Logger log = LoggerFactory.getLogger(PostsController.class);
	
	@Autowired
	private PostService postService;
	
	@Value("${posts.page-size}")
	private int postsListSize;
	@Value("${posts.top-list.page-size}")
	private int topPostsListSize;
	
	/**
	 * Get post by post id.
	 * 
	 * @param id is the post id. 
	 * 
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Response<PostDTO>> getPost(@NotNull @PathVariable("id") Long id) {
		
		Response<PostDTO> response = new Response<>();
		Optional<Post> optPost = postService.findPostByPostId(id);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to find the specified post.");
			response.addError("It was not possible to find the specified post.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = optPost.get(); 
		response.setData(DtoUtils.postToPostDTO(post));
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Create a new post
	 * 
	 */
	@PostMapping
	public ResponseEntity<Response<PostDTO>> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
		
		Response<PostDTO> response = new Response<>();
				
		if(bindingResult.hasErrors()) {
			log.error("It was not possible to create the specified post.");
			bindingResult.getAllErrors().forEach(err -> response.addError(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Post post = DtoUtils.postDTOtoPost(postDTO);
		Optional<Post> optPost = postService.create(post);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to create the specified post.");
			response.addError("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
		
		postDTO.setCreationDate(optPost.get().getCreationDate());
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setPostId(optPost.get().getPostId());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Update a specified post.
	 * 
	 */
	@PutMapping
	public ResponseEntity<Response<PostDTO>> updatePost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {
		
		Response<PostDTO> response = new Response<>();
		
		if(bindingResult.hasErrors()) {
			log.error("It was not possible to update the specified post. Invalid data.");
			bindingResult.getAllErrors().forEach(err -> response.addError(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = DtoUtils.postDTOtoPost(postDTO);
		Optional<Post> optPost = postService.update(post);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to update the specified post. Internal error.");
			response.addError("It was not possible to update the post.");
			return ResponseEntity.badRequest().body(response);
		}
		postDTO.setPostId(optPost.get().getPostId());
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setCreationDate(optPost.get().getCreationDate());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Get a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 * 
	 * @param length is the size of the post list that will be returned.
	 * 
	 */
	@GetMapping
	public ResponseEntity<Response<Page<PostDTO>>> getPosts(Model model, @RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="ord", defaultValue="lastUpdateDate") String ord, @RequestParam(value="dir", defaultValue="DESC") String dir) { 
		
		Response<Page<PostDTO>> response = new Response<>();
		PageRequest pageRequest = PageRequest.of(page, this.postsListSize, Direction.valueOf(dir), ord);
		Optional<Page<Post>> optLatestPosts = postService.findPosts(pageRequest);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of posts.");
			response.addError("It was not possible to create the list of posts.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Page<PostDTO> posts = optLatestPosts.get().map(post -> DtoUtils.postToPostDTO(post));
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Get a list of n latest posts of a specified user.
	 * 
	 * @param length is the size of the post list that will be returned.
	 * @param userId is the user identification. 
	 * 
	 */
	@GetMapping("/byuser/{userid}") 
	public ResponseEntity<Response<ArrayList<PostDTO>>> getPostListByUser(@PathVariable("userid") Long userId, 
			@RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="ord", defaultValue="lastUpdateDate") String ord, 
			@RequestParam(value="dir", defaultValue="DESC") String dir) { 
		
		Response<ArrayList<PostDTO>> response = new Response<>();
		PageRequest pageRequest = PageRequest.of(page, this.postsListSize, Direction.valueOf(dir), ord);
		Optional<Page<Post>> optLatestPosts = postService.findPostsByUserId(userId, pageRequest);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to get the list of posts.");
			response.addError("It was not possible to get the list of posts.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostDTO> posts = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			posts.add(DtoUtils.postToPostDTO(post));
		});
		
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/summaries") 
	public ResponseEntity<Response<ArrayList<SummaryDTO>>> getSummarylist(Model model, @RequestParam(value="cat", defaultValue="all") String cat) { 
		
		Response<ArrayList<SummaryDTO>> response = new Response<>();
		Optional<Page<Post>> optLatestPosts;
		PageRequest page = PageRequest.of(0, this.postsListSize, Sort.by("lastUpdateDate"));
		if(cat.toLowerCase().equals("all")) {
			optLatestPosts = postService.findPosts(page);
		}else {
			optLatestPosts = postService.findPostsByCategory(cat, page);
		}
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of summaries.");
			response.addError("It was not possible to create the list of summaries.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<SummaryDTO> summaries = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			SummaryDTO summaryDTO = new SummaryDTO(post.getPostId(), post.getTitle(), 
					post.getCreationDate(),	post.getLastUpdateDate(), 
					post.getSummary(), post.getAuthor().getUserName(), post.getTags());
			summaries.add(summaryDTO);
		});	
		response.setData(summaries);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/top") 
	public ResponseEntity<Response<ArrayList<PostInfo>>> getTopPostsInfoList(Model model) { 
		
		log.info("Getting a list of post information (title + id)");
		
		Response<ArrayList<PostInfo>> response = new Response<>();
		PageRequest page = PageRequest.of(0, this.topPostsListSize, Sort.by("lastUpdateDate"));
		Optional<Page<Post>> optLatestPosts = postService.findPosts(page);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of info list.");
			response.addError("It was not possible to create the list of info list.");
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
	
	/**
	 * 
	 * Delete a specific post.
	 * 
	 * @param id is the id of the post that will be deleted.
	 * @return
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Response<PostDTO>> deletePost(@PathVariable("id") Long id){
		log.info("Deleting post ...");
		Response<PostDTO> response = new Response<>();
		
		Optional<Post> optPost = postService.deleteByPostId(id);
		
		if(!optPost.isPresent()) {
			response.addError("It was not possible to find the specified post.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = optPost.get(); 
		response.setData(DtoUtils.postToPostDTO(post));
		
		return ResponseEntity.ok(response);
	}
	
}
