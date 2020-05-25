package com.blog.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class FeedbackDTO {
	
	private Long id;
	private String userName;
	private String email;
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime feedbackDate;

}
