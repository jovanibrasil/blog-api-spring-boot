package com.blog.services.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.services.PostService;

@Service
@Primary
public class PostServiceJpaImpl implements PostService {

	private static final Logger log = LoggerFactory.getLogger(PostServiceJpaImpl.class);

	@Value("${filesystem.blog.images}")
	private String imagesDir;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private FileSystemStorageService storage;

	@Override
	public Optional<Page<Post>> findPosts(Pageable page) {
		return Optional.of(this.postRepo.findPosts(page));
	}

	@Override
	public Optional<Page<Post>> findPostsByCategory(String category, Pageable page) {
		return Optional.of(this.postRepo.findPostsByCategory(category, page));
	}

	@Override
	public Optional<Page<Post>> findPostsByUserName(String userName, Pageable page) {
		return Optional.of(this.postRepo.findPostsByUserName(userName, page));
	}

	@Override
	public Optional<Post> findPostByPostId(Long id) {
		return this.postRepo.findById(id);
	}

	private void savePostImageFiles(MultipartFile[] postImages, Long postId) {
		// save post images
		for (MultipartFile image : postImages) {
			this.storage.saveImage(image, postId);
		}
	}
	
	@Override
	public Optional<Post> create(Post post, MultipartFile[] postImages) {
		Optional<Post> optPost = this.findPostByPostId(post.getPostId());
		if (!optPost.isPresent()) {
			post.setLastUpdateDate(LocalDateTime.now());
			post.setCreationDate(LocalDateTime.now());
			log.info("Saving post into mysql database. \n{}", post);
			post = this.postRepo.save(post);
			if(post!= null) { 
				if(postImages.length == 0) {
					post.setBannerUrl("/images/image-not-found.png");
				}else {
					post.setBannerUrl("/images/" + post.getPostId() + "/" + postImages[0].getOriginalFilename());
					this.savePostImageFiles(postImages, post.getPostId()); 
				}
				post = this.postRepo.save(post);	
			}
			return Optional.of(post);
		} else {
			return Optional.empty();
		}
	}
	
	/**
	 * Create a post without information. The created post
	 * contains only id and update/creation dates. 
	 * 
	 */
	@Override
	public Optional<Post> create() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = new User(userName);
		Post post = new Post(user);
		post.setLastUpdateDate(LocalDateTime.now());
		post.setCreationDate(LocalDateTime.now());
		log.info("Saving post into mysql database. \n{}", post);
		post = this.postRepo.save(post);
		return Optional.of(post);
	}

	@Override
	public Optional<Post> update(Post receivedPost, MultipartFile[] postImages) {
		Optional<Post> optPost = this.findPostByPostId(receivedPost.getPostId());
		if (optPost.isPresent()) {
			Post post = optPost.get();
			post.setTitle(receivedPost.getTitle());
			post.setBody(receivedPost.getBody());
			post.setSummary(receivedPost.getSummary());
			post.setLastUpdateDate(LocalDateTime.now());
			post.setTags(receivedPost.getTags());
			if(post!= null) { 
				if(postImages.length == 0) {
					post.setBannerUrl("/images/image-not-found.png");
				}else {
					post.setBannerUrl("/images/" + post.getPostId() + "/" + postImages[0].getOriginalFilename());
					this.savePostImageFiles(postImages, post.getPostId()); 
				}
				post = this.postRepo.save(post);
			}
			return Optional.of(post);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Post> deleteByPostId(Long postId) {
		Optional<Post> post = this.findPostByPostId(postId);
		if (post.isPresent()) {
			this.postRepo.deleteById(postId);
			this.storage.deletePostDirectory(postId);
			return post;
		}
		return Optional.empty();
	}

	

}
