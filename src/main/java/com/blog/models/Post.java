package com.blog.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	
	@Column(nullable=false)
	private String title;

	@Column(nullable=false, columnDefinition = "TIMESTAMP")
	private LocalDateTime creationDate;

	@Column(nullable=false, columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateDate;
	
	@Column(nullable=false, length=1000)
	private String summary;
	
	// Lob: data should be represented as BLOB (binary data) in the database.
	// Any serializable data can be annotated with this notation.
	@Lob @Column(nullable=false)
	private String body;
	
	// FetchType: JPA loads all data together or on-demand.
	// In this case, author will be loaded together.
	@ManyToOne(fetch=FetchType.EAGER)// Many posts to one user.
	@JoinColumn(referencedColumnName="userName", name="user_name")
	@JsonBackReference
	private User author;
	
	@Column(name="tag", nullable=false)
	@ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	@CollectionTable(name="post_tags", joinColumns = @JoinColumn(name = "post_id"))
	private List<String> tags;
	
	@Column(nullable = false)
	private String bannerUrl;

	public Post(User author) {
		this.postId = 0L;
		this.title = "";
		this.summary = "";
		this.body = "";
		this.author = author;
		this.lastUpdateDate = LocalDateTime.now();
		this.creationDate = LocalDateTime.now();
		this.tags = new ArrayList<String>(); 
	}
	
	public Post(Long id, String title, String summary, List<String> tags, String body, User author) {
		this.postId = id;
		this.title = title;
		this.summary = summary;
		this.body = body;
		this.author = author;
		this.lastUpdateDate = LocalDateTime.now();
		this.tags = tags; 
	}
	
	public Post(String title, String summary,  List<String> tags, String body, User author) {
		this.title = title;
		this.summary = summary;
		this.body = body;
		this.author = author;
		this.creationDate = LocalDateTime.now();
		this.lastUpdateDate = LocalDateTime.now();
		this.tags = tags;
	}

	public void addTag(String tag) {
		if(this.tags == null) {
			this.tags = new ArrayList<>();
		}
		this.tags.add(tag);
	}

}
