package com.blog.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter @Setter @NoArgsConstructor
public class UserForm {
	
	@NonNull @Positive
	private Long userId;
	@Length(min=4, max=12, message="{error.user.name.size}")
    @NotBlank(message="{error.user.name.notblank}")
	private String userName;
	@NotBlank(message="{error.user.email.notblank}")
	@Email @Length(max=40, message="{error.user.email.size}")
	private String email;
    
	// Extra user information
	@Length(max=60, message="{error.user.name.full.maxsize}")
	private String fullUserName;
	@Length(max=20, message="{error.user.phone.maxsize}")
    private String phone;
    
	// Network references
    @Length(max=20, message="{error.user.github.maxsize}")
    private String githubUserName;
    @Length(max=20, message="{error.user.linkedin.maxsize}")
    private String linkedinUserName;
    @Length(max=100, message="{error.user.googlescholar.maxsize}")
    private String googlescholarLink;

}
