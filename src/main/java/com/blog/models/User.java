package com.blog.models;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.blog.enums.ProfileTypeEnum;
import lombok.*;

@Getter
@Setter
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
