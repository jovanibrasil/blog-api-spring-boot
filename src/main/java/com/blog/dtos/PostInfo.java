package com.blog.dtos;

public class PostInfo {

	private Long id;
	private String title;
	
	public PostInfo(Long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	
}
