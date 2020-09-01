package com.blog.model.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FeedbackForm {
	
	@NotBlank(message = "{error.user.name.notblank}")
	@Size(min = 4, max=16, message="{error.user.name.size}")
	private String userName;
	@NotBlank(message = "{error.user.email.notblank}")
	@Email(message="{error.user.email.invalid}")
	@Size(max=40, message="{error.user.email.size}")
	private String email;
	@Size(min=10, max=255, message="{error.feedback.size}")
	@NotBlank(message = "{error.feedback.notblank}")
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime feedbackDate;

}
