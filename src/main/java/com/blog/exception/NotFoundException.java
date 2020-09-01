package com.blog.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3425578223885199226L;

	public NotFoundException(String msg) {
		super(msg);
	}
	
}
