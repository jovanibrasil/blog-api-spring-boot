package com.blog.integrations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.blog.dtos.SummaryDTO;
import com.blog.exceptions.MicroServiceIntegrationException;

@Component
public class SearchClient {

	@Value("${urls.search}")
	private String uri;
	
	public List<SummaryDTO> searchSummaries(String query) {
		try {
			List<SummaryDTO> summaries = new ArrayList<>();
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(uri, Object[].class);
			for (Object object : responseEntity.getBody()) {
				summaries.add((SummaryDTO)object);
			}
			return summaries;
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not possible to complete the search.", e);
		}
	}
	
}
