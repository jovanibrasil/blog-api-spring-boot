package com.blog.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.forms.SummaryForm;
import com.blog.services.impl.SearchServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/search")
@Slf4j
@RequiredArgsConstructor
public class SearchController {

	private final SearchServiceImpl searchClient;

	@GetMapping
	public ResponseEntity<List<SummaryForm>> getSearchSummaries(@RequestParam("filter") String query){
		log.info("Searching by {}", query);
		List<SummaryForm> summaries = searchClient.searchSummaries(query);
		return ResponseEntity.ok(summaries);		
	}
	
}
