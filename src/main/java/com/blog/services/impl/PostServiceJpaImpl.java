package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
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
	public Optional<List<Post>> findAll() {
		return Optional.of(this.postRepo.findAll());
	}

	@Override
	public Optional<Page<Post>> findPosts(Pageable page) {
		return Optional.of(this.postRepo.findPosts(page));
	}

	@Override
	public Optional<Page<Post>> findPostsByCategory(String category, Pageable page) {
		return Optional.of(this.postRepo.findPostsByCategory(category, page));
	}
	
	@Override
	public Optional<Page<Post>> findPostsByUser(String userName, Pageable page) {
		return Optional.of(this.postRepo.findPostsByUserId(userName, page));
	}

	@Override
	public Optional<Post> findById(Long id) {
		return this.postRepo.findById(id);
	}

	@Override
	public Optional<Post> create(Post post) {
		post.setLastUpdateDate(new Date());
		return Optional.of(this.postRepo.save(post));
	}

	@Override
	public Optional<Post> edit(Post post) {
		return Optional.of(this.postRepo.save(post));
	}

	@Override
	public Optional<Post> deleteById(Long id) {
		Optional<Post> post = this.findById(id);
		if(post.isPresent()) {
			this.postRepo.deleteById(id);
			return post;
		}
		return Optional.empty();
	}


}
