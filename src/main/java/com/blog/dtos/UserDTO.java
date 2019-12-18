package com.blog.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

@Getter @Setter @NoArgsConstructor
public class UserDTO {
	
	@NonNull
	private Long userId;
	// basic user data
	@Length(min=4, max=16, message="The user name must contains between 4 and 16 characters.")
    @NotBlank(message="The user name must not be empty.")
	private String userName;
	@NotBlank(message="The email must not be empty.")
	@Email @Length(max=30, message="The email must contains a maximum of 60 characters.")
	private String email;
    
	// Extra user information
	@Length(max=60, message="The full user name must contains a maximum of 60 characters.")
	private String fullUserName;
	@Length(max=20, message="The github name must contains a maximum of 20 characters.")
    private String phone;
    
	// network references
    @Length(max=20, message="The github user name must contains a maximum of 20 characters.")
    private String githubUserName;
    @Length(max=20, message="The linkedin user name must contains a maximum of 20 characters.")
    private String linkedinUserName;
    @Length(max=100, message="The Google Scholar link must contains a maximum of 100 characters.")
    private String googlescholarLink;

}
