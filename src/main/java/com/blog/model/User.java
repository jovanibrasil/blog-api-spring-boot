package com.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.blog.model.enums.ProfileTypeEnum;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@Entity @Table(name="users")
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
	@Column(nullable=false, columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateDate;
	@Column(nullable=false)
	private LocalDateTime creationDate;
	
	public User() {
		this.lastUpdateDate = LocalDateTime.now();
		this.creationDate = LocalDateTime.now();
	}

	public User(String userName) {
		this.userName = userName;
	}

}
