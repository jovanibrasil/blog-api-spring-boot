package com.blog.controllers;

import com.blog.dtos.FeedbackDTO;
import com.blog.exceptions.CustomMessageSource;
import com.blog.models.Feedback;
import com.blog.response.Response;
import com.blog.services.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feedbacks")
@Slf4j
public class FeedbackController {

	private FeedbackService feedbackService;
	private CustomMessageSource msgSrc;

	public FeedbackController(FeedbackService feedbackService, CustomMessageSource msgSrc) {
		this.feedbackService = feedbackService;
		this.msgSrc = msgSrc;
	}

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
		Feedback feedback = new Feedback(feedbackDTO.getUserName(),
				feedbackDTO.getEmail(), feedbackDTO.getContent());
		
		Optional<Feedback> optFeedback = this.feedbackService.create(feedback);
		
		if(!optFeedback.isPresent()) {
			log.error("It was not possible to create the feedback.");
			response.setData(msgSrc.getMessage("error.feedback.creation"));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData("Feedback enviado com sucesso.");
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Retrieves a list of feedbacks.
	 * 
	 * @return
	 */
	@GetMapping 
	public ResponseEntity<Response<ArrayList<FeedbackDTO>>> getFeedbacks() {
		
		Response<ArrayList<FeedbackDTO>> response = new Response<>();
		log.info("Retrieving a list of feedbacks ...");
		Optional<List<Feedback>> optLatestFeedbacks = this.feedbackService.findFeedbacks(2L);
		
		if(!optLatestFeedbacks.isPresent()) {
			log.error("It was not possible to create the list of feedbacks.");
			response.addError(msgSrc.getMessage("error.feedback.findall"));
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<FeedbackDTO> feedbacks = new ArrayList<>();
		
		optLatestFeedbacks.get().forEach(feedback -> {
			System.out.println(feedback);
			FeedbackDTO feedbackDTO = FeedbackDTO.builder()
					.userName(feedback.getUserName())
					.email(feedback.getEmail())
				    .content(feedback.getContent())
					.build();
			feedbacks.add(feedbackDTO);
		});
		
		response.setData(feedbacks);
		return ResponseEntity.ok(response);
		
	}
	
}
