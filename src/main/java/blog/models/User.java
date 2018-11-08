package blog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=30, unique=true)
	private String userName;
	@Column(length=60)
	private String passwordHash;
	@Column(length=100)
	private String fullName;
	
	@OneToMany(mappedBy="author")
	private Set<Post> posts = new HashSet<>();
	
	public User() {}
	
	public User(Long id, String name, String fullName) {
		super();
		this.id = id;
		this.userName = name;
		this.fullName = fullName;
	}
	
	public Long getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public String getFullName() {
		return fullName;
	}
	public Set<Post> getPosts() {
		return posts;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passwordHash=" + passwordHash + ", fullName=" + fullName
				+ ", posts=" + posts + "]";
	}
	
}
