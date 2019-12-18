package com.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FeedbackDTO {
	
	@NotBlank(message = "Email must not be null or empty")
	@Size(min = 2, max=40, message="Username must contains between 2 and 40 characters.")
	private String userName;
	@NotBlank(message = "Email must not be blank")
	@Email(message="Email must be valid.")
	@Size(max=40, message="Username must not contains more than 40 characters.")
	private String email;
	@Size(min=10, max=255, message="Feedback content must contains between 10 and 255 characters.")
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime feedbackDate;

}
