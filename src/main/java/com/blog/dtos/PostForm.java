package com.blog.dtos;

import org.springframework.web.multipart.MultipartFile;

public class PostForm {

	private PostDTO postDTO;
	private MultipartFile[] images;
	
	public PostDTO getPostDTO() {
		return postDTO;
	}
	public void setPostDTO(PostDTO postDTO) {
		this.postDTO = postDTO;
	}
	public MultipartFile[] getImages() {
		return images;
	}
	public void setImages(MultipartFile[] images) {
		this.images = images;
	}
	
}
