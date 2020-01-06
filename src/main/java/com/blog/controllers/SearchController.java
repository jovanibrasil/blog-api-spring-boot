package com.blog.controllers;

import com.blog.dtos.SummaryDTO;
import com.blog.integrations.SearchClient;
import com.blog.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
