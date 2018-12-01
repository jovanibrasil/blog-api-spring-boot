package blog.presentation.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import blog.enums.ProfileTypeEnum;

@Entity
@Table(name="users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 4524066694717395806L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Column(nullable=false, length=30, unique=true)
	private String userName;
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private ProfileTypeEnum profileType;
	@Column(length=60)
	private String passwordHash;
	@Column(length=100)
	private String fullUserName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date lastUpdateDate;
	// One user to many posts.
	@OneToMany(mappedBy="author", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Post> posts = new HashSet<>();
	
	public User() {}
	
	public User(Long id, String name, String fullName) {
		super();
		this.userId = id;
		this.userName = name;
		this.fullUserName = fullName;
	}
	
	public ProfileTypeEnum getProfileType() {
		return profileType;
	}

	public void setProfileType(ProfileTypeEnum profileType) {
		this.profileType = profileType;
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdateDate = new Date();
	}
	
	@PrePersist
	public void prePersist() {
		this.lastUpdateDate = new Date();
	}
	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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