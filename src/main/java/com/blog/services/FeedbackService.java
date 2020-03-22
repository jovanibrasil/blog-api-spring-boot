package com.blog.services;

import com.blog.models.Feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
	
	Page<Feedback> findAll(Pageable page);
	Page<Feedback> findFeedbacks(Pageable page);
	Page<Feedback> findFeedbacksByUser(String userName, Pageable page);
	Feedback findById(Long id);
	Feedback create(Feedback feedback);
	Feedback edit(Feedback feedback);
	Feedback deleteById(Long id);
	
}
