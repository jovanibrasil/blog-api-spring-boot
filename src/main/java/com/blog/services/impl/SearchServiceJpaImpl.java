package com.blog.services.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.model.Post;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.mapper.SummaryMapper;
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
	public Page<PostSummaryDTO> searchSummaries(String query, Pageable pageable) {
		Page<Post> posts = postRepository.findByTerm(query, pageable);
		return posts.map(summaryMapper::postToSummaryDto);
	}	
	
}
