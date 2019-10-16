package com.blog.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.lang.NonNull;

import com.blog.enums.ProfileTypeEnum;

@Entity
@Table(name="users")
public class User {
	
	// Basic information
	@Id 
	@Column(nullable=false, length=16)
	private String userName;
	@Column(nullable=false, length=60) 
	private String email;
	
	// Extra information
	@Column(length=60)
	private String fullUserName;
	@Column(length=20)
	private String phoneNumber;
	
	// Social networks
	@Column(length=20)
	private String githubUserName;
	@Column(length=20)
	private String linkedinUserName;
	@Column(length=100)
	private String googleScholarLink;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private ProfileTypeEnum profileType;
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(nullable=false)
	private Date lastUpdateDate;
	@Column(nullable=false)
	private Date creationDate;
	
	public User() {
		this.lastUpdateDate = new Date();
		this.creationDate = new Date();
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getGoogleScholarLink() {
		return googleScholarLink;
	}

	public void setGoogleScholarLink(String googleScholarUserName) {
		this.googleScholarLink = googleScholarUserName;
	}

	public ProfileTypeEnum getProfileType() {
		return profileType;
	}

	public void setProfileType(ProfileTypeEnum profileType) {
		this.profileType = profileType;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
