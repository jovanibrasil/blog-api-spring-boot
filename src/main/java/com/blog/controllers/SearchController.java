package com.blog.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dtos.SummaryDTO;
import com.blog.services.impl.SearchServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/search")
@Slf4j
@RequiredArgsConstructor
public class SearchController {

	private final SearchServiceImpl searchClient;

	@GetMapping
	public ResponseEntity<List<SummaryDTO>> getSearchSummaries(@RequestParam("filter") String query){
		log.info("Searching by {}", query);
		List<SummaryDTO> summaries = searchClient.searchSummaries(query);
		return ResponseEntity.ok(summaries);		
	}
	
}
