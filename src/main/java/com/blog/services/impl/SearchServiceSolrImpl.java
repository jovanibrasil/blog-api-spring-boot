package com.blog.services.impl;

import com.blog.exception.MicroServiceIntegrationException;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchServiceSolrImpl implements SearchService {

	@Value("${urls.search}")
	private String uri;
	private final RestTemplate restTemplate;

	@Override
	public Page<PostSummaryDTO> searchSummaries(String query, Pageable pageable) {
		try {
			List<PostSummaryDTO> summaries = new ArrayList<>();
			
			URI searchURI = URI.create(String.format("%s?term=%s", uri, query));
			
			ResponseEntity<PostSummaryDTO[]> responseEntity = restTemplate
					.getForEntity(searchURI, PostSummaryDTO[].class);
			Collections.addAll(summaries, responseEntity.getBody());
			return new PageImpl<>(summaries);
		} catch (Exception e) {
			throw new MicroServiceIntegrationException("It was not possible to complete the search.", e);
		}
	}
	
}
