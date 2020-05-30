package com.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;
	
	@Column(nullable=false)
	private String title;

	@Column(nullable=false, columnDefinition = "TIMESTAMP")
	private LocalDateTime creationDate = LocalDateTime.now();

	@Column(nullable=false, columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateDate = LocalDateTime.now();
	
	@Column(nullable=false, length=1000)
	private String summary;
	
	// Lob: data should be represented as BLOB (binary data) in the database.
	@Lob @Column(nullable=false)
	private String body;
	
	@ManyToOne(fetch=FetchType.EAGER)// Many posts are related to one user.
	@JoinColumn(referencedColumnName="userName", name="user_name")
	@JsonBackReference
	private User author;
	
	@Column(name="tag", nullable=false)
	@ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	@CollectionTable(name="post_tags", joinColumns = @JoinColumn(name = "post_id"))
	private List<String> tags = new ArrayList<>();
	
	@Column
	private int likes;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "image_id")
	private Image banner;

	public void addTag(String tag) {
		if(this.tags == null) {
			this.tags = new ArrayList<>();
		}
		this.tags.add(tag);
	}

	public void incrementLikes() {
		if(this.likes < Integer.MAX_VALUE) {
			this.likes++;
		}
	}

}
