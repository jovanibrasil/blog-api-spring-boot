package blog.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = -4524066694717395806L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Column(nullable=false, length=30, unique=true)
	private String userName;
	@Column(length=60)
	private String passwordHash;
	@Column(length=100)
	private String fullUserName;
	
	@OneToMany(mappedBy="author")
	private Set<Post> posts = new HashSet<>();
	
	public User() {}
	
	public User(Long id, String name, String fullName) {
		super();
		this.userId = id;
		this.userName = name;
		this.fullUserName = fullName;
	}
	
	public Long getId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public String getFullUserName() {
		return fullUserName;
	}
	public Set<Post> getPosts() {
		return posts;
	}
	public void setUserId(Long id) {
		this.userId = id;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setFullUserName(String fullName) {
		this.fullUserName = fullName;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", userName=" + userName + ", passwordHash=" + passwordHash + ", fullName=" + fullUserName
				+ ", posts=" + posts + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public String getPassword() {
		return this.passwordHash;
	}

	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}
	
}
