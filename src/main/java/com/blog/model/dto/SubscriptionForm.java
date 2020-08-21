package com.blog.model.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
public class SubscriptionForm {

	@Email(message = "{error.user.email.format}")
	private String email;
	
}
