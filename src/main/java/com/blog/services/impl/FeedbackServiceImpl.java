package com.blog.services.impl;

import com.blog.exceptions.NotFoundException;
import com.blog.models.Feedback;
import com.blog.repositories.FeedbackRepository;
import com.blog.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

	private final FeedbackRepository feedbackRepo;

	@Override
	public Page<Feedback> findAll(Pageable page) {
		return feedbackRepo.findAll(page);
	}

	@Override
	public Page<Feedback> findFeedbacks(Pageable page) {
		return feedbackRepo.findAll(page);
	}

	@Override
	public Page<Feedback> findFeedbacksByUser(String userName, Pageable page) {
		return feedbackRepo.findByUserId(userName, page);
	}

	@Override
	public Feedback findById(Long id) {
		Optional<Feedback> optFeedback = feedbackRepo.findById(id);
		if(!optFeedback.isPresent()) {
			throw new NotFoundException("Feedback not found");
		}
		return optFeedback.get();
	}

	@Override
	public Feedback create(Feedback feedback) {
		return feedbackRepo.save(feedback);
	}

	@Override
	public Feedback edit(Feedback feedback) {
		return feedbackRepo.save(feedback);
	}

	@Override
	public Feedback deleteById(Long id) {
		Feedback optFeedback = findById(id);
		this.feedbackRepo.delete(optFeedback);
		return optFeedback;
	}
	
}
