package com.blog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dtos.SummaryDTO;
import com.blog.integrations.SearchClient;
import com.blog.models.Post;
import com.blog.response.Response;
import com.blog.services.PostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private SearchClient searchClient;
	
	public ResponseEntity<Response<List<SummaryDTO>>> getSearchSummaries(@RequestParam("filter") String query){

		Response<List<SummaryDTO>> response = new Response<>();
		Optional<List<Post>> optLatestPosts;
		
		List<SummaryDTO> summaries = searchClient.searchSummaries(query);
		
		response.setData(summaries);
		return ResponseEntity.ok(response);
		
	}
	
	
}
