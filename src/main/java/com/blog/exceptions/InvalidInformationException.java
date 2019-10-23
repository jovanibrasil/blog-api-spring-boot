package com.blog.exceptions;

import java.util.Arrays;
import java.util.List;

public class InvalidInformationException extends RuntimeException {

	private static final long serialVersionUID = 7657335828184665725L;

	List<String> errors;
 	
	public InvalidInformationException(String message) {
		super(message);
		this.errors = Arrays.asList("message");
	}

	public List<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}
	
}
