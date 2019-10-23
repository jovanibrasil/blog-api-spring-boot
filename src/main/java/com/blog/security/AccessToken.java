package com.blog.security;

import com.blog.integrations.AuthClient;

public class AccessToken {

	private static String token = null;
	
	private AccessToken() {
		token = (new AuthClient()).getServiceToken();	
	}
	
	public static String getToken() {
		if(token == null) 
			new AccessToken(); 
		return token;
	}
	
}
