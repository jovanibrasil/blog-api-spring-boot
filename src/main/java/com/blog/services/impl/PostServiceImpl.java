package com.blog.services.impl;

import com.blog.exceptions.NotFoundException;
import com.blog.models.Image;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.ImageService;
import com.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	@Value("${filesystem.blog.images}")
	private String imagesDir;

	private final PostRepository postRepository;
	private final ImageService imageService;

	@Override
	public Page<Post> findPosts(Pageable page) {
		return this.postRepository.findAll(page);
	}

	@Override
	public Page<Post> findPostsByCategory(String category, Pageable page) {
		return this.postRepository.findByCategory(category, page);
	}

	@Override
	public Page<Post> findPostsByUserName(String userName, Pageable page) {
		return this.postRepository.findByUserName(userName, page);
	}

	@Override
	public Post findPostById(Long id) {
		Optional<Post> optPost = this.postRepository.findById(id);
		if(optPost.isPresent()) {
			return optPost.get();
		}
		log.error("It was not possible to find the post with id %d.", id);
		throw new NotFoundException("error.post.find");
	}

	@Transactional
	@Override
	public Post update(Post receivedPost) {
		
		Post post = this.findPostById(receivedPost.getId());
		post.setTitle(receivedPost.getTitle());
		post.setBody(receivedPost.getBody());
		post.setSummary(receivedPost.getSummary());
		post.setLastUpdateDate(LocalDateTime.now());
		post.setTags(receivedPost.getTags());
		post.getBanner().setContent(receivedPost.getBanner().getMultipartBanner());
		return post;
	}

	@Transactional
	@Override
	public Post deleteByPostId(Long postId) {
		Post post = this.findPostById(postId);
		this.postRepository.deleteById(postId);
		return post;
	}
	
	/**
	 * Creates a post with the received information.
	 */
	@Transactional
	@Override
	public Post create(Post post) {
		log.info("Saving post banner.");
		Image banner = imageService.saveImage(post.getBanner());
		post.setBanner(banner);
		log.info("Getting post related video.");
		//String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		String userName = "jovanibrasil";
		User user = new User(userName);
		post.setAuthor(user);
		log.info("Saving post.");
		post.setLastUpdateDate(LocalDateTime.now());
		post.setCreationDate(LocalDateTime.now());
		post = this.postRepository.save(post);
		
		return post;
	}

}
