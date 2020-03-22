package com.blog.services;

import com.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
	
	Page<Post> findPosts(Pageable page);
	Page<Post> findPostsByCategory(String category, Pageable page);
	Page<Post> findPostsByUserName(String userName, Pageable page);
	Post findPostById(Long id);
	Post update(Post post, MultipartFile[] postImages);
	Post deleteByPostId(Long id);
	Post create();
	
}
