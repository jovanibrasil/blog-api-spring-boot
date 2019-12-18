package com.blog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dtos.FeedbackDTO;
import com.blog.models.Feedback;
import com.blog.response.Response;
import com.blog.services.FeedbackService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
	
	/**
	 * Saves an user feedback.
	 * 
	 * @param feedbackDTO
	 * @param bindingResult
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response<String>> saveFeedback(
			@Valid @RequestBody FeedbackDTO feedbackDTO, 
			BindingResult bindingResult){
		
		Response<String> response = new Response<>();
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(err -> response.addError(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		log.info("Saving feedback ...");
		
		Feedback feedback = new Feedback(feedbackDTO.getUserName(), 
				feedbackDTO.getEmail(), feedbackDTO.getContent());
		
		Optional<Feedback> optFeedback = this.feedbackService.create(feedback);
		
		if(!optFeedback.isPresent()) {
			log.error("It was not possible to create the feedback.");
			response.setData("It was not possible to create the feedback.");
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
			response.addError("It was not possible to create the list of feedbacks.");
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
