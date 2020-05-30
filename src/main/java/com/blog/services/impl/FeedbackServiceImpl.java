package com.blog.services.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.exception.NotFoundException;
import com.blog.model.Feedback;
import com.blog.model.dto.FeedbackDTO;
import com.blog.model.form.FeedbackForm;
import com.blog.model.mapper.FeedbackMapper;
import com.blog.repositories.FeedbackRepository;
import com.blog.services.FeedbackService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

	private final FeedbackRepository feedbackRepo;
	private final FeedbackMapper feedbackMapper;

	/**
	 * Retrieves a list of feedback.
	 * 
	 */
	@Override
	public Page<FeedbackDTO> findFeedback(Pageable page) {
		return feedbackRepo.findAll(page)
				.map(feedbackMapper::feedbackToFeedbackDto);
	}

	@Override
	public Page<FeedbackDTO> findFeedbacksByUser(String userName, Pageable page) {
		return feedbackRepo.findByUserId(userName, page)
				.map(feedbackMapper::feedbackToFeedbackDto);
	}

	/**
	 * Retrieves a specific feedback.
	 * 
	 */
	@Override
	public FeedbackDTO findById(Long id) {
		return feedbackRepo.findById(id)
				.map(feedbackMapper::feedbackToFeedbackDto)
				.orElseThrow(() -> new NotFoundException("Feedback not found"));
	}

	/**
	 * Saves an user feedback.
	 */
	@Override
	public FeedbackDTO create(FeedbackForm feedbackForm) {
		Feedback feedback = feedbackMapper.feedbackFormToFeedback(feedbackForm);
		return feedbackMapper.feedbackToFeedbackDto(feedbackRepo.save(feedback));
	}

	
}
