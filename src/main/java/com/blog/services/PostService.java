package com.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostInfoDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.form.PostForm;

public interface PostService {
	
	PostDTO findPostById(Long id);
	Page<PostDTO> findPosts(Pageable page);
	Page<PostDTO> findPostsByCategory(String category, Pageable page);
	Page<PostDTO> findPostsByUserName(String userName, Pageable page);
	Page<PostSummaryDTO> findPostSummaryList(String cat, Pageable verifyReceivedPageable);
	Page<PostInfoDTO> findPostInfoList(Pageable pageable);
	
	void deleteByPostId(Long id);
	PostDTO create(PostForm postForm, MultipartFile banner);
	PostDTO update(Long postId, PostForm postForm, MultipartFile banner);
	void incrementLikes(Long postId);
	
}
