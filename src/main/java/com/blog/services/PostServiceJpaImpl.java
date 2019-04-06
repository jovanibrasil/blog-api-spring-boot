package com.blog.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.blog.enums.ListOrderType;
import com.blog.models.Post;
import com.blog.repositories.PostRepository;

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
	public Optional<Page<Post>> findPosts(Long limit) {
		return Optional.of(this.postRepo.findPosts(limit, null));
	}

	@Override
	public Optional<Page<Post>> findPostsByCategory(String category, Long limit) {
		return Optional.of(this.postRepo.findPostsByCategory(category, limit, null));
	}
	
	@Override
	public Optional<Page<Post>> findPostsByUser(String userName, Long quantity) {
		return Optional.of(this.postRepo.findPostsByUserId(userName, quantity, null));
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
