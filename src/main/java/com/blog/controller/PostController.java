package com.blog.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostInfoDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.form.PostForm;
import com.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@Value("${blog.posts.page-size}")
	private int postsListSize;
	@Value("${blog.posts.top-list.page-size}")
	private int topPostsListSize;

	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<PostDTO> getPosts(
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable){
		return postService.findPosts(verifyReceivedPageable(pageable));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public PostDTO getPost(@PathVariable("id") Long id) {
		return postService.findPostById(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/byuser/{username}") 
	public Page<PostDTO> getPostsByUser(@PathVariable("username") String userId, 
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable){
		return postService.findPostsByUserName(userId, verifyReceivedPageable(pageable));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/summaries")
	public Page<PostSummaryDTO> getPostSummaryList(
			@PageableDefault(page = 0, size = 6, direction = Direction.DESC, sort = "creationDate") Pageable pageable,
			@RequestParam(value="category", defaultValue="all") String cat) {
		return postService.findPostSummaryList(cat, verifyReceivedPageable(pageable));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/top") 
	public Page<PostInfoDTO> getTopPostInfoList() {
		PageRequest page = PageRequest.of(0, this.topPostsListSize, Sort.by(Direction.DESC, "lastUpdateDate"));
		return postService.findPostInfoList(page);
	}
	
	private Pageable verifyReceivedPageable(Pageable pageable) {
		int pageSize = pageable.getPageSize() <= this.postsListSize ? pageable.getPageSize() : this.postsListSize; 
		return PageRequest.of(pageable.getPageNumber(), pageSize, pageable.getSort());
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
		PostDTO postDTO = postService.create(postForm, banner);
		
		URI uri = uriBuilder.path("/posts/{id}")
				.buildAndExpand(postDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(path = "/{postId}",  consumes = { "multipart/form-data" })
	public PostDTO updatePost(
			@PathVariable Long postId,
			@RequestPart(name = "post") @Valid @NotNull PostForm postForm,
			@RequestPart MultipartFile banner) {
		
		log.info("Updating post ...");
		return postService.update(postId, postForm, banner);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value="/{postId}")
	public void deletePost(@PathVariable("postId") Long postId){
		log.info("Deleting post ...");
		postService.deleteByPostId(postId);
	}
	
}
