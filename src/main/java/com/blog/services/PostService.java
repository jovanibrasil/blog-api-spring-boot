package com.blog.services;

import com.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PostService {
	
	Optional<Page<Post>> findPosts(Pageable page);
	Optional<Page<Post>> findPostsByCategory(String category, Pageable page);
	Optional<Page<Post>> findPostsByUserName(String userName, Pageable page);
	Optional<Post> findPostByPostId(Long id);
	Optional<Post> create(Post post, MultipartFile[] postImages);
	Optional<Post> update(Post post, MultipartFile[] postImages);
	Optional<Post> deleteByPostId(Long id);
	Optional<Post> create();
	
}
