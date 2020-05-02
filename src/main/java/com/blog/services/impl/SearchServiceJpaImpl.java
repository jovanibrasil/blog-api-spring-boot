package com.blog.services.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.dtos.SummaryDTO;
import com.blog.mappers.SummaryMapper;
import com.blog.models.Post;
import com.blog.repositories.PostRepository;
import com.blog.services.SearchService;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class SearchServiceJpaImpl implements SearchService {

	private final PostRepository postRepository;
	private final SummaryMapper summaryMapper;
	
	@Override
	public Page<SummaryDTO> searchSummaries(String query, Pageable pageable) {
		Page<Post> posts = postRepository.findByTerm(query, pageable);
		return posts.map(summaryMapper::postToSummaryDto);
	}	
	
}
