package com.blog.services;

import java.util.List;
import java.util.Optional;

import com.blog.models.Feedback;
import com.blog.models.Post;

public interface FeedbackService {
	
	public Optional<List<Feedback>> findAll();
	public Optional<List<Feedback>> findFeedbacks(Long n);
	public Optional<List<Feedback>> findFeedbacksByUser(String userName, Long n);
	public Optional<Feedback> findById(Long id);
	public Optional<Feedback> create(Feedback feedback);
	public Optional<Feedback> edit(Feedback feedback);
	public Optional<Feedback> deleteById(Long id);
	
}
