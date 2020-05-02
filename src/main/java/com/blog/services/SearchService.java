package com.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.dtos.SummaryDTO;

public interface SearchService {
	Page<SummaryDTO> searchSummaries(String query, Pageable pageable);
}
