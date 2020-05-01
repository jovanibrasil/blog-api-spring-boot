package com.blog.services.impl;

import com.blog.exceptions.MicroServiceIntegrationException;
import com.blog.forms.SummaryForm;
import com.blog.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

	@Value("${urls.search}")
	private String uri;
	private final RestTemplate restTemplate;

	@Override
	public List<SummaryForm> searchSummaries(String query) {
		try {
			List<SummaryForm> summaries = new ArrayList<>();
			
			URI searchURI = URI.create(String.format("%s?term=%s", uri, query));
			
			ResponseEntity<SummaryForm[]> responseEntity = restTemplate
					.getForEntity(searchURI, SummaryForm[].class);
			Collections.addAll(summaries, responseEntity.getBody());
			return summaries;
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not possible to complete the search.", e);
		}
	}
	
}
