package com.blog.models;

import java.util.ArrayList;
import java.util.Arrays;
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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blog.enums.ProfileTypeEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 4524066694717395806L;

	@Id
	@Column(nullable=false, length=30, unique=true)
	private String userName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private ProfileTypeEnum profileType;
	
	@Column(length=100)
	private String fullUserName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date lastUpdateDate;
	
	public User() {}
	
	public User(String userName, String fullName, ProfileTypeEnum profileType) {
		super();
		this.userName = userName;
		this.fullUserName = fullName;
		this.lastUpdateDate = new Date();
		this.profileType = profileType;
	}

	public User(String userName, ProfileTypeEnum profileType) {
		super();
		this.userName = userName;
		this.profileType = profileType;
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

	public String getFullUserName() {
		return fullUserName;
	}
	
	public void setUserName(String name) {
		this.userName = name;
	}
	
	public void setFullUserName(String fullName) {
		this.fullUserName = fullName;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(Arrays.asList(profileType));
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
	
	@Override
	public String toString() {
		return "User [ userName=" + userName + ", profileType=" + profileType 
				+ " , fullUserName=" + fullUserName + ", lastUpdateDate=" + lastUpdateDate + ", posts=";
	}

	@Override
	public String getPassword() {
		return null;
	}
	
}
