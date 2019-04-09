package com.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.blog.enums.ListOrderType;
import com.blog.models.Post;

public interface PostService {
	
	public Optional<List<Post>> findAll();
	public Optional<Page<Post>> findPosts(Pageable page);
	public Optional<Page<Post>> findPostsByCategory(String category, Pageable page);
	public Optional<Page<Post>> findPostsByUser(String userName, Pageable page);
	public Optional<Post> findById(Long id);
	public Optional<Post> create(Post post);
	public Optional<Post> edit(Post post);
	public Optional<Post> deleteById(Long id);
	
}
