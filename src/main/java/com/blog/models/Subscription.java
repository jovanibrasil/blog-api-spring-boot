package com.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="subscription")
public class Subscription {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, unique = true)
	private String email;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime subscriptionDate;

	public Subscription(String email) {
		this.email = email;
		this.subscriptionDate = LocalDateTime.now();
	}

}
