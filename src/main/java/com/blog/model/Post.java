package com.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
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
	private LocalDateTime creationDate;

	@Column(nullable=false, columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateDate;
	
	@Column(nullable=false, length=1000)
	private String summary;
	
	@Column(nullable=false)
	private String body;
	
	@ManyToOne(fetch=FetchType.EAGER)// Many posts are related to one user.
	@JoinColumn(referencedColumnName="userName", name="user_name")
	@JsonBackReference
	private User author;
	
	@Column(name="tag", nullable=false)
	@ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	@CollectionTable(name="post_tags", joinColumns = @JoinColumn(name = "post_id"))
	private List<String> tags;
	
	@Column
	private int likes;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "image_id")
	private Image banner;
	
	public Post() {
		this.creationDate = LocalDateTime.now();
		this.lastUpdateDate = LocalDateTime.now();
		this.tags = new ArrayList<>();
	}

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
