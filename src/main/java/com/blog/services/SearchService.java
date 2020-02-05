package com.blog.services;

import com.blog.dtos.SummaryDTO;

import java.util.List;

public interface SearchService {
    List<SummaryDTO> searchSummaries(String query);
}
