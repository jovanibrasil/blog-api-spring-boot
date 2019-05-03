package com.blog.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.models.Post;
import com.blog.repositories.PostRepository;
import com.blog.services.PostService;

@Service
@Primary
public class PostServiceJpaImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Override
	public Optional<Page<Post>> findPosts(Pageable page) {
		return Optional.of(this.postRepo.findPosts(page));
	}

	@Override
	public Optional<Page<Post>> findPostsByCategory(String category, Pageable page) {
		return Optional.of(this.postRepo.findPostsByCategory(category, page));
	}
	
	@Override
	public Optional<Page<Post>> findPostsByUserId(Long userId, Pageable page) {
		return Optional.of(this.postRepo.findPostsByUserId(userId, page));
	}

	@Override
	public Optional<Post> findPostByPostId(Long id) {
		return this.postRepo.findById(id);
	}

	@Override
	public Optional<Post> create(Post post) {
		Optional<Post> optPost = this.findPostByPostId(post.getPostId());
		if(!optPost.isPresent()) {
			post.setLastUpdateDate(new Date());
			post.setCreationDate(new Date());
			return Optional.of(this.postRepo.save(post));
		}else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Post> update(Post receivedPost) {
		Optional<Post> optPost = this.findPostByPostId(receivedPost.getPostId());
		if(optPost.isPresent()) {
			Post post = optPost.get();
			post.setTitle(receivedPost.getTitle());
			post.setBody(receivedPost.getBody());
			post.setSummary(receivedPost.getSummary());
			post.setLastUpdateDate(new Date());
			return Optional.of(this.postRepo.save(post));
		}else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Post> deleteByPostId(Long id) {
		Optional<Post> post = this.findPostByPostId(id);
		if(post.isPresent()) {
			this.postRepo.deleteById(id);
			return post;
		}
		return Optional.empty();
	}

}
