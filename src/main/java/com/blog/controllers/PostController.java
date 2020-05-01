package com.blog.controllers;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.dtos.PostDTO;
import com.blog.dtos.PostInfoDTO;
import com.blog.dtos.SummaryDTO;
import com.blog.forms.PostForm;
import com.blog.mappers.PostInfoMapper;
import com.blog.mappers.PostMapper;
import com.blog.mappers.SummaryMapper;
import com.blog.models.Image;
import com.blog.models.Post;
import com.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final PostInfoMapper postInfoMapper;
	private final PostMapper postMapper;
	private final SummaryMapper summaryMapper;
	
	@Value("${blog.posts.page-size}")
	private int postsListSize;
	@Value("${blog.posts.top-list.page-size}")
	private int topPostsListSize;

	/**
	 * Returns a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 *
	 */
	@GetMapping
	public ResponseEntity<Page<PostDTO>> getPosts(
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable){
		
		Page<PostDTO> posts = postService.findPosts(verifyReceivedPageable(pageable))
				.map(postMapper::postToPostDto);
		
		return ResponseEntity.ok(posts);
		
	}
	
	/**
	 * Returns a post given a post id.
	 * 
	 * @param id is the post id. 
	 * 
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPost(@NotNull @PathVariable("id") Long id) {
		Post post = postService.findPostById(id);
		PostDTO postDto = postMapper.postToPostDto(post);
		return ResponseEntity.ok(postDto);
	}
	
	/**
	 * Returns a list of n latest posts of a specified user.
	 *
	 * @param userId is the user identification. 
	 * 
	 */
	@GetMapping("/byuser/{username}") 
	public ResponseEntity<Page<PostDTO>> getPostsByUser(@PathVariable("username") String userId, 
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable){
		
		Page<PostDTO> posts = postService.findPostsByUserName(userId, verifyReceivedPageable(pageable))
				.map(postMapper::postToPostDto);
		
		return ResponseEntity.ok(posts);
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
	public ResponseEntity<Page<SummaryDTO>> getSummaries(
			@PageableDefault(page = 0, size = 6, direction = Direction.DESC, sort = "creationDate") Pageable pageable,
			@RequestParam(value="category", defaultValue="all") String cat) {
			
		log.info("Get a list of post summaries. category");
		
		pageable = verifyReceivedPageable(pageable);
		
		Page<Post> latestPosts = cat.equalsIgnoreCase("all") ? 
			 postService.findPosts(pageable) : postService.findPostsByCategory(cat, pageable);
		
		Page<SummaryDTO> summaries = latestPosts.map(summaryMapper::postToSummaryDto);
			
		return ResponseEntity.ok(summaries);
	}
	
	/**
	 * Returns a list of PostInfo objects. A PostInfo object contains id and title of
	 * an post. The size of the list id determined by TOP_POSTS_LIST_SIZE.
	 * 
	 * @return
	 */
	@GetMapping("/top") 
	public ResponseEntity<Page<PostInfoDTO>> getTopPostsInfoList() {
		
		log.info("Getting a list of post information (title + id)");
		PageRequest page = PageRequest.of(0, this.topPostsListSize, Sort.by(Direction.DESC, "lastUpdateDate"));
		Page<PostInfoDTO> postInfoDTOList = postService.findPosts(page)
				.map(postInfoMapper::postToPostInfoDto);
		
		return ResponseEntity.ok(postInfoDTOList);
		
	}
	
	private Pageable verifyReceivedPageable(Pageable pageable) {
		int pageSize = pageable.getPageSize() <= this.postsListSize ? pageable.getPageSize() : this.postsListSize; 
		return PageRequest.of(pageable.getPageNumber(), 
				pageSize, pageable.getSort());
	}
		
	/**
	 * Creates a new post.
	 * 
	 */
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<PostDTO> createPost(
			@RequestPart(name = "post") @Valid @NotNull PostForm postForm, 
			@RequestPart(name = "banner") @NotNull MultipartFile banner,
			UriComponentsBuilder uriBuilder) {
		
		log.info("Creating post ...");
		Post post = postMapper.postFormToPost(postForm);
		post.setBanner(new Image(banner));
		post = postService.create(post);
		
		URI uri = uriBuilder.path("/posts/{id}")
				.buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Updates a specified post.
	 * 
	 */
	@PutMapping(path = "/{postId}",  consumes = { "multipart/form-data" })
	public ResponseEntity<PostDTO> updatePost(
			@PathVariable Long postId,
			@RequestPart(name = "post") @Valid @NotNull PostForm postForm,
			@RequestPart MultipartFile banner) {
		
		log.info("Updating post ...");
		Post post = postMapper.postFormToPost(postForm);
		post.setId(postId);
		post.setBanner(new Image(banner));
		post = postService.update(post);
		PostDTO postDto = postMapper.postToPostDto(post);
		return ResponseEntity.ok(postDto);
	}
	
	/**
	 * 
	 * Deletes a specific post.
	 * 
	 * @param id is the id of the post that will be deleted.
	 * @return
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id){
		log.info("Deleting post ...");
		postService.deleteByPostId(id);
		return ResponseEntity.noContent().build();
	}
	
}
