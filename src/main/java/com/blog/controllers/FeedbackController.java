package com.blog.controllers;

import com.blog.dtos.FeedbackDTO;
import com.blog.exceptions.CustomMessageSource;
import com.blog.mappers.FeedbackMapper;
import com.blog.models.Feedback;
import com.blog.response.Response;
import com.blog.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {

	private final FeedbackService feedbackService;
	private final CustomMessageSource msgSrc;
	private final FeedbackMapper feedbackMapper;

	/**
	 * Saves an user feedback.
	 * 
	 * @param feedbackDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response<String>> saveFeedback(
			@Valid @RequestBody FeedbackDTO feedbackDTO){
		
		Response<String> response = new Response<>();
		
		log.info("Saving feedback ...");
		Feedback feedback = feedbackMapper.feedbackDtoToFeedback(feedbackDTO);
		Optional<Feedback> optFeedback = this.feedbackService.create(feedback);
		
		if(!optFeedback.isPresent()) {
			log.error("It was not possible to create the feedback.");
			response.setData(msgSrc.getMessage("error.feedback.creation"));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData("Feedback has been successfully sent.");
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Retrieves a list of feedbacks.
	 * 
	 * @return
	 */
	@GetMapping 
	public ResponseEntity<Response<List<FeedbackDTO>>> getFeedback() {
		
		Response<List<FeedbackDTO>> response = new Response<>();
		log.info("Retrieving a list of feedback ...");
		Optional<List<Feedback>> optLatestFeedbacks = this.feedbackService.findFeedbacks(2L);
		
		if(!optLatestFeedbacks.isPresent()) {
			log.error("It was not possible to create the list of feedback.");
			response.addError(msgSrc.getMessage("error.feedback.findall"));
			return ResponseEntity.badRequest().body(response);
		}
		
		List<FeedbackDTO> feedback = optLatestFeedbacks.get()
				.stream()
				.map(feedbackMapper::feedbackToFeedbackDto)
				.collect(Collectors.toList());
		
		response.setData(feedback);
		return ResponseEntity.ok(response);
		
	}
	
}
