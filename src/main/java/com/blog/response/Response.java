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
	private List<String> errors;
	
	public Response() {}
	
	public Response(T data, List<String> errors) {
		super();
		this.data = data;
		this.errors = errors;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<String> getErrors() {
		if(this.errors == null) {
			this.errors = new ArrayList<>();
		}
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
