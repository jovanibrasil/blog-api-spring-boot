package com.blog.controllers;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dtos.SummaryDTO;
import com.blog.integrations.SearchClient;
import com.blog.response.Response;

@CrossOrigin("*")
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

	private SearchClient searchClient;

	public SearchController(SearchClient searchClient) {
		this.searchClient = searchClient;
	}

	@GetMapping
	public ResponseEntity<Response<List<SummaryDTO>>> getSearchSummaries(@RequestParam("filter") String query){
		log.info("Searching by {}", query);
		Response<List<SummaryDTO>> response = new Response<>();
		
		List<SummaryDTO> summaries = searchClient.searchSummaries(query);
		
		response.setData(summaries);
		return ResponseEntity.ok(response);
		
	}
	
	
}
