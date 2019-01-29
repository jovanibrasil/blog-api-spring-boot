package blog.presentation.models;

import java.util.Date;

import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	@NotNull
	@Size(min=2, max=40, message="Título do Post deve ter entre 2 e 10 caracteres.")
	@Column(nullable=false, length=300)
	private String title;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date lastUpdateDate;
	// Lob: data should be represented as BLOB (binary data) in the database.
	// Any serializable data can be annotated with this notation.
	@NotNull
	@Size(min=2, max=20000, message="Corpo do post deve ter entre 2 e 1000 caracteres.")
	@Lob @Column(nullable=false)
	private String body;
	// FetchType: JPA loads all data together or on-demand.
	// In this case, author will be loaded on demand
	// Many posts to one user.
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="userId", name="user_id")
	@JsonBackReference
	private User author;
	
	public Post() {}
	
	public Post(Long id, String title, String body, User author) {
		this.postId = id;
		this.title = title;
		this.body = body;
		this.author = author;
		this.lastUpdateDate = new Date();
	}
	
	public Post(String title, String body, User author) {
		this.title = title;
		this.body = body;
		this.author = author;
		this.lastUpdateDate = new Date();
	}

	public Long getPostId() {
		return postId;
	}
	
	public void setPostId(Long id) {
		this.postId = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date date) {
		this.lastUpdateDate = date;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", title=" + title + "]";
	}
	
}
