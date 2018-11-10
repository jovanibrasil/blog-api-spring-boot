package blog.models;

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

@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	@Column(nullable=false, length=300)
	private String title;
	// Lob: data should be represented as BLOB (binary data) in the database.
	// Any serializable data can be annotated with this notation.
	@Lob @Column(nullable=false)
	private String body;
	// FetchType: JPA loads all data together or on-demand.
	// In this case, author will be loaded on demand
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="userId")
	private User author;
	
	@Column(nullable=false)
	private Date date = new Date();
	
	public Post() {}
	
	public Post(Long id, String title, String body, User author) {
		this.postId = id;
		this.title = title;
		this.body = body;
		this.author = author;
	}

	public Long getId() {
		return postId;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public User getAuthor() {
		return author;
	}
	public Date getDate() {
		return date;
	}
	public void setId(Long id) {
		this.postId = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
