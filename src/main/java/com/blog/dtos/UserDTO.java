package com.blog.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDTO {
	
	private Long userId;
	private String userName;
	private String email;
    
	private String fullUserName;
	private String phone;
    
	private String githubUserName;
    private String linkedinUserName;
    private String googlescholarLink;

}
