package com.blog.integrations;

import com.blog.config.BlogServiceProperties;
import com.blog.exceptions.MicroServiceIntegrationException;
import com.blog.security.TempUser;
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
public class AuthClient {

	@Value("${urls.auth.check-token}")
	private String checkTokenUrl;
	
	@Value("${urls.auth.get-token}")
	private String getTokenUrl;

	private BlogServiceProperties blogServiceProperties;

	public AuthClient(BlogServiceProperties blogServiceProperties){
		this.blogServiceProperties = blogServiceProperties;
	}

	public TempUser checkToken(String token) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<Response<TempUser>> responseEntity = restTemplate.exchange(checkTokenUrl, HttpMethod.GET, entity, 
					new ParameterizedTypeReference<Response<TempUser>>() {} );
			
			return responseEntity.getBody().getData();
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not posssible to validate the user.", e);
		}
	}
	
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
			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
			
			// send request and parse result
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> loginResponse = restTemplate
			  .exchange(getTokenUrl, HttpMethod.POST, entity, String.class);
			JSONObject responseBody = (new JSONObject(loginResponse.getBody()));
			if (loginResponse.getStatusCode() == HttpStatus.OK) {
				//return 
				JSONObject responseData = responseBody.getJSONObject("data");
				return responseData.getString("token");
			} else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				// bad credentials
				// TODO return errors?
			}
			return "";
			
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not posssible to validate the user.", e);
		}
	}
	
}
