package com.blog.dtos;

import com.blog.models.Post;
import com.blog.models.User;

public class DtoUtils {

	/**
	 * Converts a Post object to a PostDTO object.
	 * 
	 * @param post
	 * @return
	 */
	public static PostDTO postToPostDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setPostId(post.getPostId());
		postDTO.setUserName(post.getAuthor().getUserName());
		postDTO.setSummary(post.getSummary());
		postDTO.setBody(post.getBody());
		postDTO.setCreationDate(post.getCreationDate());
		postDTO.setLastUpdateDate(post.getLastUpdateDate());
		postDTO.setTitle(post.getTitle());
		postDTO.setTags(post.getTags());
		postDTO.setUserId(post.getAuthor().getUserId());
		return postDTO;
	}
	
	/**
	 * Converts a PostDTO object to a Post object.
	 * 
	 * @param postDTO
	 * @return
	 */
	public static Post postDTOtoPost(PostDTO postDTO) {
		Post post = new Post();
		post.setPostId(postDTO.getPostId());
		post.setSummary(postDTO.getSummary());
		post.setBody(postDTO.getBody());
		post.setCreationDate(postDTO.getCreationDate());
		post.setLastUpdateDate(postDTO.getLastUpdateDate());
		post.setTitle(postDTO.getTitle());
		post.setTags(postDTO.getTags());
		User user = new User();
		user.setUserId(postDTO.getUserId());
		post.setAuthor(user);
		return post;
	}
	
}
