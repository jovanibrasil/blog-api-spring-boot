package com.blog.security;

import com.blog.services.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class AccessToken {

	private static String token = null;

	@Autowired
	private AuthServiceImpl authClient;
	
	private AccessToken() {
		token = authClient.getServiceToken();
	}
	
	public static String getToken() {
		if(token == null) 
			new AccessToken(); 
		return token;
	}
	
}
