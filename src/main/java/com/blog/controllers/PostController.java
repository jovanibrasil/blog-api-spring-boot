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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
public class PostController {

	private static final Logger log = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PostService postService;
	
	@Value("${blog.posts.page-size}")
	private int POSTS_LIST_SIZE;
	@Value("${blog.posts.top-list.page-size}")
	private int TOP_POSTS_LIST_SIZE;
	
	/**
	 * Retrieves a post given a post id.
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
	 * Creates a new empty post. 
	 * 
	 */
	@PostMapping("/empty")
	public ResponseEntity<Response<PostDTO>> createPost() {
		Response<PostDTO> response = new Response<>();
		
		log.info("Creating new empty post");
		Optional<Post> optPost = postService.create();
		
		if(!optPost.isPresent()) {
			log.info("It was not possible to create the specified post.");
			response.addError("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
		PostDTO postDTO = new PostDTO();
		postDTO.setCreationDate(optPost.get().getCreationDate());
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setId(optPost.get().getPostId());
		response.setData(postDTO);

		return ResponseEntity.ok(response);
	}
	
	/**
	 * Creates a new post
	 * 
	 */
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<Response<PostDTO>> createPost(
			@RequestPart(name = "post") @Valid @NotNull PostDTO postDTO,
			@RequestPart @NotNull MultipartFile[] postImages, BindingResult bindingResult) {
		
		log.info("Multiparts received: " + postImages.length);
		
		Response<PostDTO> response = new Response<>();
				
		if(bindingResult.hasErrors()) {
			log.info("It was not possible to create the specified post. Invalid fields.");
			bindingResult.getAllErrors().forEach(err -> response.addError(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = DtoUtils.postDTOtoPost(postDTO);
		log.info("Creating new post");
		Optional<Post> optPost = postService.create(post, postImages);
		
		if(!optPost.isPresent()) {
			log.info("It was not possible to create the specified post.");
			response.addError("It was not possible to created the post.");
			return ResponseEntity.badRequest().body(response);
		}
		
		postDTO.setCreationDate(optPost.get().getCreationDate());
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setId(optPost.get().getPostId());
		response.setData(postDTO);

		return ResponseEntity.ok(response);
	}
	
	/**
	 * Updates a specified post.
	 * 
	 */
	@PutMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<Response<PostDTO>> updatePost(
			@RequestPart(name = "post") @Valid @NotNull PostDTO postDTO,
			@RequestPart MultipartFile[] postImages, 
			BindingResult bindingResult) {
		
		log.info("Updating post ...");
		Response<PostDTO> response = new Response<>();
		if(bindingResult.hasErrors()) {
			log.error("It was not possible to update the specified post. Invalid data.");
			bindingResult.getAllErrors().forEach(err -> response.addError(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = DtoUtils.postDTOtoPost(postDTO);
		Optional<Post> optPost = postService.update(post, postImages);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to update the specified post. Internal error.");
			response.addError("It was not possible to update the post.");
			return ResponseEntity.badRequest().body(response);
		}
		postDTO.setId(optPost.get().getPostId());
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setCreationDate(optPost.get().getCreationDate());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Retrieves a list of n latest posts of a specified user.
	 * 
	 * @param length is the size of the post list that will be returned.
	 * @param userId is the user identification. 
	 * 
	 */
	@GetMapping("/byuser/{username}") 
	public ResponseEntity<Response<ArrayList<PostDTO>>> getPostsByUser(@PathVariable("username") String userId, 
			@RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="ord", defaultValue="lastUpdateDate") String ord, 
			@RequestParam(value="dir", defaultValue="DESC") String dir) { 
		
		Response<ArrayList<PostDTO>> response = new Response<>();
		PageRequest pageRequest = PageRequest.of(page, this.POSTS_LIST_SIZE, Direction.valueOf(dir), ord);
		Optional<Page<Post>> optLatestPosts = postService.findPostsByUserName(userId, pageRequest);
		
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
	
	/**
	 * Retrieves a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 * 
	 * @param length is the size of the post list that will be returned.
	 * 
	 */
	@GetMapping
	public ResponseEntity<Response<Page<PostDTO>>> getPosts(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="ord", defaultValue="lastUpdateDate") String ord, @RequestParam(value="dir", defaultValue="DESC") String dir) { 
		
		Response<Page<PostDTO>> response = new Response<>();
		PageRequest pageRequest = PageRequest.of(page, this.POSTS_LIST_SIZE, Direction.valueOf(dir), ord);
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
	 * Retrieves a list of post summaries. A post summary is an object with basic information
	 * about a specific post, like id, title and tags.  
	 * 
	 * @param page
	 * @param cat
	 * @return
	 */
	@GetMapping(value = "/summaries")
	public ResponseEntity<Response<Page<SummaryDTO>>> getSummaries(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="category", defaultValue="all") String cat) { 
		log.info("Get a list of post summaries. category: {}", cat);
		Response<Page<SummaryDTO>> response = new Response<>();
		Optional<Page<Post>> optLatestPosts;
		PageRequest pageRequest = PageRequest.of(page, this.POSTS_LIST_SIZE, Sort.by("creationDate"));
		if(cat.toLowerCase().equals("all")) {
			optLatestPosts = postService.findPosts(pageRequest);
		}else {
			optLatestPosts = postService.findPostsByCategory(cat, pageRequest);
		}
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of summaries.");
			response.addError("It was not possible to create the list of summaries.");
			return ResponseEntity.badRequest().body(response);
		}
		
		Page<SummaryDTO> summaries = optLatestPosts.get()
				.map(post -> {
					return new SummaryDTO(post.getPostId(), 
							post.getTitle(), post.getCreationDate(),	
							post.getLastUpdateDate(), post.getSummary(), 
							post.getAuthor().getUserName(), post.getTags(), 
							post.getBannerUrl());
			});
			
		response.setData(summaries);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retrieves a list of PostInfo objects. A PostInfo object contains id and title of
	 * an post. The size of the list id determined by TOP_POSTS_LIST_SIZE.
	 * 
	 * @return
	 */
	@GetMapping("/top") 
	public ResponseEntity<Response<ArrayList<PostInfo>>> getTopPostsInfoList() { 
		
		log.info("Getting a list of post information (title + id)");
		
		Response<ArrayList<PostInfo>> response = new Response<>();
		PageRequest page = PageRequest.of(0, this.TOP_POSTS_LIST_SIZE, Sort.by("lastUpdateDate"));
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
	 * Deletes a specific post.
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
