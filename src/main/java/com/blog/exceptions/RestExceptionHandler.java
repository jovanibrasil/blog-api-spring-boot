package com.blog.exceptions;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blog.response.ErrorDetail;
import com.blog.response.Response;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@ExceptionHandler(InvalidInformationException.class)
	public ResponseEntity<Response<?>> handleInvalidInformationException(InvalidInformationException invalidInformationException){
		log.info("Invalid information. Sending response ...");
		Response<?> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(invalidInformationException.getMessage())
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Response<?>> handle(ValidationException validationException){
		log.info("Validation exception. Sending response ...");
		Response<?> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(validationException.getMessage())
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
}
