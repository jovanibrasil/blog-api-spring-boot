package com.blog.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	private final FeedbackService feedbackService;

	@ApiOperation("Cria um feedback.")
	@ApiResponses({@ApiResponse(code = 200, message = "Feedback criado com sucesso.", response = FeedbackDTO.class)})
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public FeedbackDTO saveFeedback(@Valid @RequestBody FeedbackForm feedbackForm, UriComponentsBuilder uriBuilder){
		log.info("Saving feedback ...");
		return feedbackService.create(feedbackForm);
	}

	@ApiOperation(value = "Busca feedbacks.")
	@ApiResponses({@ApiResponse(code = 200, message = "Resultado da busca.", response = FeedbackDTO.class, responseContainer = "Page")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping 
	public Page<FeedbackDTO> getFeedback(Pageable pageable) {
		return feedbackService.findFeedback(pageable);
	}
	
}
