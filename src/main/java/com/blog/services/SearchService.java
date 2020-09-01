package com.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.model.dto.PostSummaryDTO;

public interface SearchService {
	Page<PostSummaryDTO> searchSummaries(String query, Pageable pageable);
}
