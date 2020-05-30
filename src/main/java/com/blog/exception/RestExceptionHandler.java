package com.blog.exception;

import com.blog.response.ErrorDetail;
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
	public ResponseEntity<ErrorDetail> handleNotFoundException(NotFoundException notFoundException){
		log.info("NotFoundException. Sending response ...");
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(getMessage(notFoundException.getMessage()))
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
	}
		
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ValidationError> fieldErrors = ex.getBindingResult().getFieldErrors()
				.stream().map(e -> new ValidationError(e.getDefaultMessage(),
				e.getField(), e.getRejectedValue())).collect(Collectors.toList());

		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message("Invalid field values")
				.code(status.value())
				.status(status.getReasonPhrase())
				.objectName(ex.getBindingResult().getObjectName())
				.errors(fieldErrors).build();
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorDetail);
	}

	@ExceptionHandler(InvalidInformationException.class)
	public ResponseEntity<ErrorDetail> handleInvalidInformationException(InvalidInformationException invalidInformationException){
		log.info("Invalid information. Sending response ...");
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(invalidInformationException.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorDetail);
	}
	
	@ExceptionHandler({ValidationException.class, UserException.class})
	public ResponseEntity<ErrorDetail> handle(Exception validationException){
		log.info("Validation exception. Sending response ...");
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message(getMessage(validationException.getMessage()))
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<Object> exception(Exception ex) {
		log.info("The server cannot process the received request. {}", ex.getMessage());
		ErrorDetail errorDetail = new ErrorDetail.Builder()
				.message("The server cannot process the request.")
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
	}
}
