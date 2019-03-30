package blog.models;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	
	@Column(nullable=false)
	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date creationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date lastUpdateDate;
	
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

	public Post() {}
	
	public Post(Long id, String title, String summary, List<String> tags, String body, User author) {
		this.postId = id;
		this.title = title;
		this.summary = summary;
		this.body = body;
		this.author = author;
		this.lastUpdateDate = new Date();
		this.tags = tags; 
	}
	
	public Post(String title, String summary,  List<String> tags, String body, User author) {
		this.title = title;
		this.summary = summary;
		this.body = body;
		this.author = author;
		this.creationDate = new Date();
		this.lastUpdateDate = new Date();
		this.tags = tags;
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
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public void addTag(String tag) {
		if(this.tags == null) {
			this.tags = new ArrayList<>();
		}
		this.tags.add(tag);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
