package com.blog.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.blog.exceptions.NotFoundException;
import com.blog.models.Image;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.ImageService;
import com.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ImageService imageService;

	@Override
	public Page<Post> findPosts(Pageable page) {
		return postRepository.findAll(page);
	}

	@Override
	public Page<Post> findPostsByCategory(String category, Pageable page) {
		return postRepository.findByCategory(category, page);
	}

	@Override
	public Page<Post> findPostsByUserName(String userName, Pageable page) {
		return postRepository.findByUserName(userName, page);
	}

	@Override
	public Post findPostById(Long id) {
		Optional<Post> optPost = postRepository.findById(id);
		if(optPost.isPresent()) {
			return optPost.get();
		}
		log.error("It was not possible to find the post with id %d.", id);
		throw new NotFoundException("error.post.find");
	}

	@Transactional
	@Override
	public Post update(Post receivedPost) {
		
		Post post = findPostById(receivedPost.getId());
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
		Post post = findPostById(postId);
		postRepository.deleteById(postId);
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
		
		log.info("Getting post related user.");
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = new User(userName);
		post.setAuthor(user);
		
		log.info("Saving post.");
		post.setLastUpdateDate(LocalDateTime.now());
		post.setCreationDate(LocalDateTime.now());
		post = postRepository.save(post);
		return post;
	}

}
