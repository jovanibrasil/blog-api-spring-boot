package com.blog.exceptions;

import javax.validation.ValidationException;

import com.blog.response.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blog.response.ErrorDetail;
import com.blog.response.Response;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Response<?> response = new Response<>();

		List<ValidationError> fieldErrors = ex.getBindingResult().getFieldErrors()
				.stream().map(e -> new ValidationError(e.getDefaultMessage(),
				e.getField(), e.getRejectedValue())).collect(Collectors.toList());

		ErrorDetail error = new ErrorDetail.Builder()
				.message("Invalid field values")
				.code(status.value())
				.status(status.getReasonPhrase())
				.objectName(ex.getBindingResult().getObjectName())
				.errors(fieldErrors).build();
		response.addError(error);
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
	}

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

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<Object> exception(Exception ex) {
		log.info("The server cannot process the received request. {}", ex.getMessage());
		Response<String> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message("The server cannot process the request.")
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
