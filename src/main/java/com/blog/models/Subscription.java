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
@Table(name="subscription")
public class Subscription {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, unique = true)
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionDate;

	public Subscription() {}
	
	public Subscription(Long id, String email, Date subscriptionDate) {
		super();
		this.id = id;
		this.email = email;
		this.subscriptionDate = subscriptionDate;
	}
	
	public Subscription(String email) {
		this.email = email;
		this.subscriptionDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}
	
}
