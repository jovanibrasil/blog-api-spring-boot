package com.blog.services.impl;

import com.blog.config.security.BlogServiceProperties;
import com.blog.exception.MicroServiceIntegrationException;
import com.blog.model.dto.UserDetailsDTO;
import com.blog.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableConfigurationProperties(BlogServiceProperties.class)
@RequiredArgsConstructor
public class AuthServiceJwtImpl implements AuthService {

	@Value("${urls.auth.check-token}")
	private String checkTokenUrl;
	
	@Value("${urls.auth.get-token}")
	private String getTokenUrl;

	private final BlogServiceProperties blogServiceProperties;
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
			// create request body
			JSONObject request = new JSONObject();
			request.put("username", blogServiceProperties.getUsername());
			request.put("password", blogServiceProperties.getPassword());
			request.put("applications", new JSONArray("BLOG_APP"));
			// set headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
			
			// send request and parse result
			ResponseEntity<String> loginResponse = restTemplate
			  .exchange(getTokenUrl, HttpMethod.POST, entity, String.class);
			JSONObject responseBody = (new JSONObject(loginResponse.getBody()));
			if (loginResponse.getStatusCode() == HttpStatus.OK) {
				//return 
				JSONObject responseData = responseBody.getJSONObject("data");
				return responseData.getString("token");
			} else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				// bad credentials
				// TODO return error
			}
			return "";
			
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not posssible to validate the user.", e);
		}
	}
	
}
