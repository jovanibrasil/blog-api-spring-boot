package com.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
