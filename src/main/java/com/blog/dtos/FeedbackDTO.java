package com.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	
	public FeedbackDTO() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(LocalDateTime feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	@Override
	public String toString() {
		return "FeedbackDTO [userName=" + userName + ", email=" + email + ", content=" + content + ", feedbackDate="
				+ feedbackDate + "]";
	}
	
}
