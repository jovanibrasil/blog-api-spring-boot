package com.blog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	@PostMapping
	public ResponseEntity<Response<String>> postFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO, BindingResult bindingResult){
		
		Response<String> response = new Response<>();
		
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(err -> response.addError(err.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Feedback feedback = new Feedback(feedbackDTO.getName(), 
				feedbackDTO.getEmail(), feedbackDTO.getContent());
		
		Optional<Feedback> optFeedback = this.feedbackService.create(feedback);
		
		if(!optFeedback.isPresent()) {
			response.setData("Não foi possível registrar o feedback.");
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData("Feedback enviado com sucesso.");
		return ResponseEntity.ok(response);
		
	}
	
	
	@GetMapping("/list/{length}") 
	public ResponseEntity<Response<ArrayList<FeedbackDTO>>> getPostlist(@PathVariable("length") Long length, Model model) { 
		
		Response<ArrayList<FeedbackDTO>> response = new Response<>();
		
		Optional<List<Feedback>> optLatestFeedbacks = this.feedbackService.findFeedbacks(length);
		
		if(!optLatestFeedbacks.isPresent()) {
			//log.error("It was not possible to create the list of posts.");
			response.addError("It was not possible to create the list of feedbacks.");
			return ResponseEntity.badRequest().body(response);
		}
		
		ArrayList<FeedbackDTO> feedbacks = new ArrayList<>();
		
		optLatestFeedbacks.get().forEach(feedback -> {
			System.out.println(feedback);
			FeedbackDTO feedbackDTO = new FeedbackDTO();
//			feedbackDTO.setTitle(feedback.getTitle());
//			feedbackDTO.setContent(feedback.getContent());
			feedbacks.add(feedbackDTO);
		});
		
		response.setData(feedbacks);
		return ResponseEntity.ok(response);
		
	}
	
}
