package com.blog.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FeedbackDTO {
	
	// min e max do name e do email
	
	@NotNull
	@Size(min=2, max=40, message="Nome de usuário deve ter entre 2 e 40 caracteres.")
	private String name;
	@NotNull
	@Email(message="Email must be valid.")
	private String email;
	@Size(min=10, max=1000, message="Conteudo do feedback deve ter entre 10 e 1000 caracteres.")
	private String content;
	@NotNull
	private String captchaCode;
	
	public FeedbackDTO() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	
}
