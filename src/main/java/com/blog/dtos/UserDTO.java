package com.blog.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

public class UserDTO {
	
	@NonNull
	private Long userId;
	// basic user data
	@Length(min=4, max=30, message="The user name must contains between 4 and 30 characters.")
    @NotBlank(message="The user name must not be empty.")
	private String userName;
	@NotBlank(message="The email must not be empty.")
	@Email @Length(max=30, message="The email must contains a maximum of 60 characters.")
	private String email;
    
	// Extra user information
	@Length(max=60, message="The full user name must contains a maximum of 60 characters.")
	private String fullUserName;
	@Length(max=20, message="The github name must contains a maximum of 20 characters.")
    private String phone;
    
	// network references
    @Length(max=20, message="The github user name must contains a maximum of 20 characters.")
    private String githubUserName;
    @Length(max=20, message="The linkedin user name must contains a maximum of 20 characters.")
    private String linkedinUserName;
    @Length(max=100, message="The Google Scholar link must contains a maximum of 100 characters.")
    private String googlescholarLink;
    
    public UserDTO() {}
    
    public Long getUserId() {
		return userId;
	}
    
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullUserName() {
		return fullUserName;
	}
	public void setFullUserName(String fullUserName) {
		this.fullUserName = fullUserName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGithubUserName() {
		return githubUserName;
	}

	public void setGithubUserName(String githubUserName) {
		this.githubUserName = githubUserName;
	}

	public String getLinkedinUserName() {
		return linkedinUserName;
	}

	public void setLinkedinUserName(String linkedinUserName) {
		this.linkedinUserName = linkedinUserName;
	}

	public String getGooglescholarLink() {
		return googlescholarLink;
	}

	public void setGooglescholarLink(String googlescholarLink) {
		this.googlescholarLink = googlescholarLink;
	}
	    	
}
