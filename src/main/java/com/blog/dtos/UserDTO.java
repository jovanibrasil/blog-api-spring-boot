package com.blog.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDTO {
	
	// basic user data
    @NotBlank(message="The user name must not be empty.")
	private String userName;
    @NotBlank(message="The full user name must not be empty.")
    private String fullUserName;
    @Email(message="The email is not valid.")
    @NotBlank(message="The email must not be empty.")
    private String email;
    
    private String phone;
    // network references
    private String githubUserName;
    private String linkedinUserName;
    private String googlescholarLink;
	
    public UserDTO() {}
    
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
