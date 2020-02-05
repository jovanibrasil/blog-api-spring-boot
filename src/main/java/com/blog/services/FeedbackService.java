package com.blog.services;

import com.blog.models.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
	
	Optional<List<Feedback>> findAll();
	Optional<List<Feedback>> findFeedbacks(Long n);
	Optional<List<Feedback>> findFeedbacksByUser(String userName, Long n);
	Optional<Feedback> findById(Long id);
	Optional<Feedback> create(Feedback feedback);
	Optional<Feedback> edit(Feedback feedback);
	Optional<Feedback> deleteById(Long id);
	
}
