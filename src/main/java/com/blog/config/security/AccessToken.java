package com.blog.config.security;

import com.blog.services.impl.AuthServiceJwtImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class AccessToken {

	private static String token = null;

	@Autowired
	private AuthServiceJwtImpl authClient;
	
	private AccessToken() {
		AccessToken.token = authClient.getServiceToken();
	}
	
	public static String getToken() {
		if(token == null) 
			new AccessToken(); 
		return token;
	}
	
}
