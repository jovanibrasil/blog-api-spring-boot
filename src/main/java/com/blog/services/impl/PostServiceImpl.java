package com.blog.services.impl;

import com.blog.exceptions.NotFoundException;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	@Value("${filesystem.blog.images}")
	private String imagesDir;

	private final PostRepository postRepository;
	private final FileSystemStorageServiceImpl storage;

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

	private void savePostImageFiles(MultipartFile[] postImages, Long postId) {
		// save post images
		for (MultipartFile image : postImages) {
			this.storage.saveImage(image, postId);
		}
	}
	
	/**
	 * Creates a post without information. The created post
	 * contains only id and update/creation dates. 
	 * 
	 */
	@Override
	public Post create() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = new User(userName);
		Post post = new Post(user);
		post.setLastUpdateDate(LocalDateTime.now());
		post.setCreationDate(LocalDateTime.now());
		post = this.postRepository.save(post);
		return post;
	}

	@Override
	public Post update(Post receivedPost, MultipartFile[] postImages) {
		Post post = this.findPostById(receivedPost.getPostId());
		post.setTitle(receivedPost.getTitle());
		post.setBody(receivedPost.getBody());
		post.setSummary(receivedPost.getSummary());
		post.setLastUpdateDate(LocalDateTime.now());
		post.setTags(receivedPost.getTags());
		if(postImages.length == 0) {
			post.setBannerUrl("/images/image-not-found.png");
		}else {
			post.setBannerUrl("/images/" + post.getPostId() + "/" + postImages[0].getOriginalFilename());
			this.savePostImageFiles(postImages, post.getPostId());
		}
		post = this.postRepository.save(post);
		return post;
	}

	@Override
	public Post deleteByPostId(Long postId) {
		Post post = this.findPostById(postId);
		this.postRepository.deleteById(postId);
		this.storage.deletePostDirectory(postId);
		return post;
	}

}
