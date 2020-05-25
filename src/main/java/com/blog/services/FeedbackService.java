package com.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.model.dto.FeedbackDTO;
import com.blog.model.form.FeedbackForm;

public interface FeedbackService {
	
	Page<FeedbackDTO> findFeedbacks(Pageable page);
	Page<FeedbackDTO> findFeedbacksByUser(String userName, Pageable page);
	FeedbackDTO findById(Long id);
	FeedbackDTO create(FeedbackForm feedbackForm);
	
}
