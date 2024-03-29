package com.blog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.dto.PostSummaryDTO;
import com.blog.services.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;

	@Value("${blog.posts.page-size}")
	private int postsListSize;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<PostSummaryDTO> getSearchSummaries(
			@RequestParam(name = "q", required = true) String query,
			@RequestParam(name = "page", defaultValue = "0") int pageNumber){	
		log.info("Searching by {}", query);
		PageRequest page = PageRequest.of(pageNumber, this.postsListSize, Sort.by(Direction.DESC, "creationDate"));
		return searchService.searchSummaries(query, page);		
	}
	
}
