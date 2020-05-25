package com.blog.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.dto.FeedbackDTO;
import com.blog.model.form.FeedbackForm;
import com.blog.services.FeedbackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {

	private final FeedbackService feedbackService;

	@PostMapping
	public ResponseEntity<?> saveFeedback(@Valid @RequestBody FeedbackForm feedbackForm, UriComponentsBuilder uriBuilder){
		log.info("Saving feedback ...");
		FeedbackDTO feedbackDTO = feedbackService.create(feedbackForm);
		URI uri = uriBuilder.path("/feedback/{id}")
				.buildAndExpand(feedbackDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@GetMapping("/{id}")
	public FeedbackDTO getFeedbackById(Long id) {
		return feedbackService.findById(id);	
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping 
	public Page<FeedbackDTO> getFeedback(Pageable pageable) {
		return feedbackService.findFeedbacks(pageable);
	}
	
}
