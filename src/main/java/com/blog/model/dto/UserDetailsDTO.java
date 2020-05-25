package com.blog.model.dto;

import com.blog.model.enums.ProfileTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDetailsDTO {

	private String name;
	private ProfileTypeEnum role;

}
