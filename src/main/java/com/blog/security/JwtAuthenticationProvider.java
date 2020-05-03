package com.blog.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.blog.dtos.UserDetailsDTO;
import com.blog.services.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final AuthService authService;
	
	public JwtAuthenticationProvider(AuthService authService) {
		this.authService = authService;
	}
	
	/**
	 * Process an authentication request.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		try {
			String token = (String) authentication.getCredentials();
			// Verify token with the authentication service
			UserDetailsDTO tempUser = authService.checkToken(token);
			if(tempUser != null) {
				UserDetails userDetails = new UserDetailsImpl(tempUser.getName(), tempUser.getRole()); 
				return new JwtAuthentication(token, userDetails, userDetails.getAuthorities());
			}
		} catch (Exception e) {
			log.info("Authentication problem. Probably the token is invalid.");
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(JwtAuthentication.class);
	}

}
