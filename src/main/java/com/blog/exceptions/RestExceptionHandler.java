package com.blog.exceptions;

import com.blog.response.ErrorDetail;
import com.blog.response.Response;
import com.blog.response.ValidationError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {

	private final MessageSource messageSource;

	public String getMessage(String message) {
		return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response<?>> handleNotFoundException(NotFoundException notFoundException){
		log.info("NotFoundException. Sending response ...");
		Response<?> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(getMessage(notFoundException.getMessage()))
				.build();
		response.addError(errorDetail);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
		
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
	
	@ExceptionHandler({ValidationException.class, UserException.class})
	public ResponseEntity<Response<?>> handle(Exception validationException){
		log.info("Validation exception. Sending response ...");
		Response<?> response = new Response<>();
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(getMessage(validationException.getMessage()))
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
