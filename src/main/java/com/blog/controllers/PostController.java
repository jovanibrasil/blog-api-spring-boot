package com.blog.controllers;

import com.blog.dtos.PostDTO;
import com.blog.dtos.PostInfoDTO;
import com.blog.dtos.SummaryDTO;
import com.blog.exceptions.CustomMessageSource;
import com.blog.mappers.PostInfoMapper;
import com.blog.mappers.PostMapper;
import com.blog.mappers.SummaryMapper;
import com.blog.models.Post;
import com.blog.response.Response;
import com.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final CustomMessageSource msgSrc;
	private final PostInfoMapper postInfoMapper;
	private final PostMapper postMapper;
	private final SummaryMapper summaryMapper;
	
	@Value("${blog.posts.page-size}")
	private int POSTS_LIST_SIZE;
	@Value("${blog.posts.top-list.page-size}")
	private int TOP_POSTS_LIST_SIZE;

	/**
	 * Returns a post given a post id.
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
			response.addError(msgSrc.getMessage("error.post.find"));
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = optPost.get(); 
		response.setData(postMapper.postToPostDto(post));
		
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
			response.addError(msgSrc.getMessage("error.post.create"));
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
			@RequestPart @NotNull MultipartFile[] postImages) {
		
		Response<PostDTO> response = new Response<>();

		Post post = postMapper.postDtoToPost(postDTO);
		log.info("Creating new post");
		Optional<Post> optPost = postService.create(post, postImages);

		if(!optPost.isPresent()) {
			log.info("It was not possible to create the specified post.");
			response.addError(msgSrc.getMessage("error.post.create"));
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
			@RequestPart MultipartFile[] postImages) {
		
		log.info("Updating post ...");
		Response<PostDTO> response = new Response<>();

		Post post = postMapper.postDtoToPost(postDTO);
		Optional<Post> optPost = postService.update(post, postImages);
		
		if(!optPost.isPresent()) {
			log.error("It was not possible to update the specified post. Internal error.");
			response.addError(msgSrc.getMessage("error.post.update"));
			return ResponseEntity.badRequest().body(response);
		}
		postDTO.setId(optPost.get().getPostId());
		postDTO.setLastUpdateDate(optPost.get().getLastUpdateDate());
		postDTO.setCreationDate(optPost.get().getCreationDate());
		response.setData(postDTO);
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Returns a list of n latest posts of a specified user.
	 *
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
			response.addError(msgSrc.getMessage("error.post.findall"));
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<PostDTO> posts = new ArrayList<>();
		
		optLatestPosts.get().forEach(post -> {
			posts.add(postMapper.postToPostDto(post));
		});
		
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Returns a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 *
	 */
	@GetMapping
	public ResponseEntity<Response<Page<PostDTO>>> getPosts(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="ord",
			defaultValue="lastUpdateDate") String ord,
			@RequestParam(value="dir", defaultValue="DESC") String dir) {
		
		Response<Page<PostDTO>> response = new Response<>();
		PageRequest pageRequest = PageRequest.of(page, this.POSTS_LIST_SIZE, Direction.valueOf(dir), ord);
		Optional<Page<Post>> optLatestPosts = postService.findPosts(pageRequest);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of posts.");
			response.addError(msgSrc.getMessage("error.post.findall"));
			return ResponseEntity.badRequest().body(response);
		}
		
		Page<PostDTO> posts = optLatestPosts.get()
				.map(postMapper::postToPostDto);
		response.setData(posts);
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Returns a list of post summaries. A post summary is an object with basic information
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
		PageRequest pageRequest = PageRequest.of(page, this.POSTS_LIST_SIZE, Sort.by(Direction.DESC ,"creationDate"));
		if(cat.toLowerCase().equals("all")) {
			optLatestPosts = postService.findPosts(pageRequest);
		}else {
			optLatestPosts = postService.findPostsByCategory(cat, pageRequest);
		}
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of summaries.");
			response.addError(msgSrc.getMessage("error.post.summaries"));
			return ResponseEntity.badRequest().body(response);
		}
		
		Page<SummaryDTO> summaries = optLatestPosts.get()
				.map(summaryMapper::postToSummaryDto);
			
		response.setData(summaries);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns a list of PostInfo objects. A PostInfo object contains id and title of
	 * an post. The size of the list id determined by TOP_POSTS_LIST_SIZE.
	 * 
	 * @return
	 */
	@GetMapping("/top") 
	public ResponseEntity<Response<Page<PostInfoDTO>>> getTopPostsInfoList() {
		
		log.info("Getting a list of post information (title + id)");
		
		Response<Page<PostInfoDTO>> response = new Response<>();
		PageRequest page = PageRequest.of(0, this.TOP_POSTS_LIST_SIZE, Sort.by(Direction.DESC, "lastUpdateDate"));
		Optional<Page<Post>> optLatestPosts = postService.findPosts(page);
		
		if(!optLatestPosts.isPresent()) {
			log.error("It was not possible to create the list of info list.");
			response.addError(msgSrc.getMessage("error.post.infolist"));
			return ResponseEntity.badRequest().body(response);
		}

		Page<PostInfoDTO> postInfoDTOList = optLatestPosts.get()
				.map(postInfoMapper::postToPostInfoDto);

		response.setData(postInfoDTOList);
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
			response.addError(msgSrc.getMessage("error.post.find"));
			return ResponseEntity.badRequest().body(response);
		}
		
		Post post = optPost.get(); 
		response.setData(postMapper.postToPostDto(post));
		
		return ResponseEntity.ok(response);
	}
	
}
