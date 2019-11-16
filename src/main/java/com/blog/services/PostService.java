package com.blog.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.blog.models.Post;

public interface PostService {
	
	public Optional<Page<Post>> findPosts(Pageable page);
	public Optional<Page<Post>> findPostsByCategory(String category, Pageable page);
	public Optional<Page<Post>> findPostsByUserName(String userName, Pageable page);
	public Optional<Post> findPostByPostId(Long id);
	public Optional<Post> create(Post post, MultipartFile[] postImages);
	public Optional<Post> update(Post post, MultipartFile[] postImages);
	public Optional<Post> deleteByPostId(Long id);
	public Optional<Post> create();
	
}
