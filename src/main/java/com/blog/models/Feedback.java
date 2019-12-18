package com.blog.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter
@Setter
@NoArgsConstructor
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
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime feedbackDate;

	public Feedback(String name, String email, String content) {
		this.userName = name;
		this.email = email;
		this.content = content;
		this.feedbackDate = LocalDateTime.now();
	}

}
