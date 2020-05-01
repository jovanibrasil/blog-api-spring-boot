package com.blog.services;

import java.util.List;

import com.blog.forms.SummaryForm;

public interface SearchService {
    List<SummaryForm> searchSummaries(String query);
}
