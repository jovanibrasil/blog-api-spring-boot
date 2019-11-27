package com.blog.security;

import com.blog.integrations.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;

public class AccessToken {

	private static String token = null;

	@Autowired
	private AuthClient authClient;
	
	private AccessToken() {
		token = authClient.getServiceToken();
	}
	
	public static String getToken() {
		if(token == null) 
			new AccessToken(); 
		return token;
	}
	
}
