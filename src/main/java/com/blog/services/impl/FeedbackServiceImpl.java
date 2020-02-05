package com.blog.services.impl;

import com.blog.models.Feedback;
import com.blog.repositories.FeedbackRepository;
import com.blog.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

	private final FeedbackRepository feedbackRepo;

	@Override
	public Optional<List<Feedback>> findAll() {
		return Optional.of(this.feedbackRepo.findAll());
	}

	@Override
	public Optional<List<Feedback>> findFeedbacks(Long n) {
		return Optional.of(this.feedbackRepo.findFeedbacks(n));
	}

	@Override
	public Optional<List<Feedback>> findFeedbacksByUser(String userName, Long n) {
		return Optional.of(this.feedbackRepo.findFeedbacksByUserId(userName, n));
	}

	@Override
	public Optional<Feedback> findById(Long id) {
		return this.feedbackRepo.findById(id);
	}

	@Override
	public Optional<Feedback> create(Feedback feedback) {
		return Optional.of(this.feedbackRepo.save(feedback));
	}

	@Override
	public Optional<Feedback> edit(Feedback feedback) {
		return Optional.of(this.feedbackRepo.save(feedback));
	}

	@Override
	public Optional<Feedback> deleteById(Long id) {
		Optional<Feedback> optFeedback = this.findById(id);
		if(optFeedback.isPresent()) {
			this.deleteById(id);
			return optFeedback;
		}
		return Optional.empty();
	}
	
}
