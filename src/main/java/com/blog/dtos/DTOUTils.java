package com.blog.dtos;

import com.blog.models.Post;

public class DTOUTils {

	public static PostDTO postToPostDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setUserName(post.getAuthor().getUserName());
		postDTO.setSummary(post.getSummary());
		postDTO.setBody(post.getBody());
		postDTO.setCreationDate(post.getCreationDate());
		postDTO.setLastUpdateDate(post.getLastUpdateDate());
		postDTO.setId(post.getPostId());
		postDTO.setTitle(post.getTitle());
		postDTO.setTags(post.getTags());
		return postDTO;
	}
	
}
