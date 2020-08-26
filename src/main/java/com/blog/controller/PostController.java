package com.blog.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.form.PostForm;
import com.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{postId}")
	public PostDTO getPost(@PathVariable("postId") Long postId) {
		return postService.findPostById(postId);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/summaries")
	public Page<PostSummaryDTO> getPostSummaryList(
			@PageableDefault(page = 0, size = 6, direction = Direction.DESC, sort = "creationDate") Pageable pageable,
			@RequestParam(value="category", defaultValue="all") String cat) {
		return postService.findPostSummaryList(cat, pageable);
	}
		
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<PostDTO> getPosts(@RequestParam(value = "username", defaultValue = "") String userName,
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable){
		if(!userName.isEmpty() && !userName.isBlank()) {
			return postService.findPostsByUserName(userName, pageable);
		}else {
			return postService.findPosts(pageable);	
		}
	}
		
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(consumes = { "multipart/form-data" })
	public PostDTO createPost(
			@RequestPart(name = "post", required = true) @Valid PostForm postForm, 
			@RequestPart(name = "banner", required = true) MultipartFile banner) {
		
		log.info("Creating post ...");
		return postService.create(postForm, banner);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(path = "/{postId}",  consumes = { "multipart/form-data" })
	public PostDTO updatePost(
			@PathVariable Long postId,
			@RequestPart(name = "post") @Valid @NotNull(message = "Post data is required") PostForm postForm,
			@RequestPart MultipartFile banner) {
		
		log.info("Updating post id: {}", postId);
		return postService.update(postId, postForm, banner);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{postId}/likes")
	public void likePost(@PathVariable Long postId) {
		postService.incrementLikes(postId);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value="/{postId}")
	public void deletePost(@PathVariable("postId") Long postId){
		log.info("Deleting post {}", postId);
		postService.deleteByPostId(postId);
	}
		
}
