package com.blog.services.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.blog.exception.MicroServiceIntegrationException;
import com.blog.model.dto.UserDetailsDTO;
import com.blog.services.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthServiceJwtImpl implements AuthService {

	@Value("${urls.auth.check-token}")
	private String checkTokenUrl;
	
	@Value("${urls.auth.create-token}")
	private String getTokenUrl;
	
	@Value("${blog-api.username}")
	private String userName;
	
	@Value("${blog-api.password}")
	private String password;
	
	private final RestTemplate restTemplate;

	@Override
	public UserDetailsDTO checkToken(String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<UserDetailsDTO> responseEntity = restTemplate.exchange(checkTokenUrl, HttpMethod.GET, entity,
					new ParameterizedTypeReference<UserDetailsDTO>() {} );
			return responseEntity.getBody();
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not posssible to validate the user.", e);
		}
	}

	@Override
	public String getServiceToken() {
		try {
			JSONObject request = new JSONObject();
			request.put("username", userName);
			request.put("password", password);
			request.put("applications", new JSONArray("BLOG_APP"));
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
			
			ResponseEntity<String> loginResponse = restTemplate
					.exchange(getTokenUrl, HttpMethod.POST, entity, String.class);
			JSONObject responseBody = (new JSONObject(loginResponse.getBody()));
			
			if (loginResponse.getStatusCode() == HttpStatus.OK) {
				JSONObject responseData = responseBody.getJSONObject("data");
				return responseData.getString("token");
			} else {
				throw new Exception("Service unauthorized or remote server is not accessible.");
			}
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not posssible to validate the user.", e);
		}
	}
	
}
