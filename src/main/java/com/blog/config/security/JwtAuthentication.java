package com.blog.config.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class JwtAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Object principal;
	private Object credentials;
	
	public JwtAuthentication(Object credentials) {
		super(null);
		this.principal = null;
		this.credentials = credentials;
		super.setAuthenticated(false);
	}
	
	public JwtAuthentication(Object credentials, Object principal, 
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}
	
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - set using the constructor.");
		}
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

}
