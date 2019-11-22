package com.blog.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="feedbacks")
public class Feedback {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, length = 40)
	private String userName;
	@Column(nullable=false, length = 40)
	private String email;
	@Column(nullable=false, length = 255)
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date feedbackDate;
	
	public Feedback() { }
	
	public Feedback(String name, String email, String content) {
		this.userName = name;
		this.email = email;
		this.content = content;
		this.feedbackDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", userName=" + userName + ", email=" + email + ", content=" + content
				+ ", feedbackDate=" + feedbackDate + "]";
	}
	
}
