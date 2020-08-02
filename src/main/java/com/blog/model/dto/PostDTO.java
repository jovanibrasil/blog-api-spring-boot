package com.blog.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private Long id;
	private String title;
	private String summary;
	private String body;
	
	private List<String> tags;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastUpdateDate;
	private String userName;
	private Long bannerId;
	private int likes;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this == obj) return true;
		
		if(obj instanceof PostDTO) {
			PostDTO postDTO = (PostDTO) obj;
			return Objects.deepEquals(tags, postDTO.getTags())
					&& userName.equals(postDTO.getUserName())
					&& bannerId == postDTO.getBannerId()
					&& likes == postDTO.getLikes()
					&& creationDate.equals(postDTO.getCreationDate())
					&& lastUpdateDate.equals(postDTO.getLastUpdateDate());
		}
		
		return false;
	}
	
}
