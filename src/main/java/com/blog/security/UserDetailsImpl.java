package com.blog.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blog.enums.ProfileTypeEnum;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 5179663941209606583L;
	private String userName;
	private ProfileTypeEnum profileType;
	
	public UserDetailsImpl(String userName, ProfileTypeEnum profileType) {
		super();
		this.userName = userName;
		this.profileType = profileType;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(Arrays.asList(profileType));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.userName;
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

}
