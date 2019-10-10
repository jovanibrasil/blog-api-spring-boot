package com.blog.dtos;

import org.apache.commons.codec.binary.Base64;

import com.blog.models.Post;
import com.blog.models.User;

public class DtoUtils {

	/**
	 * Encode a image represented in an array of octets (bytes) into a image represented with 
	 * a Base64 string.
	 * 
	 * @param banner
	 * @return
	 */
	public static String encodeBase64(byte[] banner) {
		if(banner == null) return "";
		if(banner.length == 0) return "";
		
		return "data:image/png;base64," + Base64.encodeBase64String(banner);
	}
	
	/**
	 * Decode a image represented in a Base64 string into a image represented with 
	 * an array of octets (bytes).
	 * 
	 * @param banner
	 * @return
	 */
	public static byte[] decodeBase64(String banner) {
		if(banner == null) return new byte[0];
		if(banner.length() == 0) return new byte[0];
		
		banner = banner.substring(banner.indexOf(',')+1);
		return Base64.decodeBase64(banner);
	}
	
	/**
	 * Converts a Post object to a PostDTO object.
	 * 
	 * @param post
	 * @return
	 */
	public static PostDTO postToPostDTO(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getPostId());
		postDTO.setUserName(post.getAuthor().getUserName());
		postDTO.setSummary(post.getSummary());
		postDTO.setBody(post.getBody());
		postDTO.setCreationDate(post.getCreationDate());
		postDTO.setLastUpdateDate(post.getLastUpdateDate());
		postDTO.setTitle(post.getTitle());
		postDTO.setTags(post.getTags());
		String banner = encodeBase64(post.getBanner());
		postDTO.setBanner(banner);
		
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
		post.setPostId(postDTO.getId());
		post.setSummary(postDTO.getSummary());
		post.setBody(postDTO.getBody());
		post.setCreationDate(postDTO.getCreationDate());
		post.setLastUpdateDate(postDTO.getLastUpdateDate());
		post.setTitle(postDTO.getTitle());
		post.setTags(postDTO.getTags());
		byte[] banner = decodeBase64(postDTO.getBanner());
		post.setBanner(banner);
		User user = new User();
		post.setAuthor(user);
		return post;
	}
	
}
