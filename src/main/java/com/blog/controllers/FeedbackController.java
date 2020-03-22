package com.blog.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.dtos.FeedbackDTO;
import com.blog.mappers.FeedbackMapper;
import com.blog.models.Feedback;
import com.blog.response.Response;
import com.blog.services.FeedbackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {

	private final FeedbackService feedbackService;
	private final FeedbackMapper feedbackMapper;

	/**
	 * Saves an user feedback.
	 * 
	 * @param feedbackDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> saveFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO, UriComponentsBuilder uriBuilder){
		log.info("Saving feedback ...");
		Feedback feedback = feedbackMapper.feedbackDtoToFeedback(feedbackDTO);
		feedback = feedbackService.create(feedback);
		URI uri = uriBuilder.path("/feedback/{id}")
				.buildAndExpand(feedback.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Retrieves a specific feedback.
	 * 
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Response<FeedbackDTO>> getFeedbackById(Long id) {
		log.info("Retrieving a feedback ...");
		Feedback feedback = feedbackService.findById(id);
		return ResponseEntity.ok(new Response<FeedbackDTO>(feedbackMapper.feedbackToFeedbackDto(feedback)));	
	}
	
	/**
	 * Retrieves a list of feedback.
	 * 
	 * @return
	 */
	@GetMapping 
	public ResponseEntity<Response<Page<FeedbackDTO>>> getFeedback(Pageable pageable) {
		
		log.info("Retrieving a list of feedback ...");
		Page<Feedback> feedbacks = feedbackService.findFeedbacks(pageable);
		Page<FeedbackDTO> feedback = feedbacks.map(feedbackMapper::feedbackToFeedbackDto);
		return ResponseEntity.ok(new Response<Page<FeedbackDTO>>(feedback));	
	}
	
}
