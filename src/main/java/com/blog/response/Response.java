package com.blog.response;

import java.util.ArrayList;
import java.util.List;

/*
 * This class encapsulates the response data and the error list.
 * 
 * @author Jovani Brasil
 *  
 */
public class Response<T> {

	private T data;
	private List<ErrorDetail> errors;
	
	public Response() {
		this.errors = new ArrayList<>();
	}
	
	public Response(T data, List<ErrorDetail> errors) {
		super();
		this.data = data;
		this.errors = errors;
	}
	
	public Response(T data) {
		this.data = data;
		this.errors = new ArrayList<>();
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public List<ErrorDetail> getErrors() {
		if(errors == null) {
			this.errors = new ArrayList<>();
		}
		return this.errors;
	}
	
	public void addError(String errorMessage) {
		this.errors.add(new ErrorDetail(errorMessage));
	}
	
	public void addError(ErrorDetail error) {
		this.errors.add(error);
	}
	
}
