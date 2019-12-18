package com.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor
public class PostDTO {

	@NotNull
	private Long id;
	
	@NotNull(message="Title must not be null")
	@Size(min=2, max=50, message="Título do Post deve ter entre 2 e 10 caracteres.")
	private String title;
	
	@NotNull(message="Summary must not be null")
	@Size(min=2, max=5000, message="Sumário deve ter entre 2 e 1000 caracteres.")
	private String summary;
	
	@Size(min=2, max=30000, message="Corpo do post deve ter entre 2 e 1000 caracteres.")
	private String body;
	
	@NotNull(message="Tags must not be null")
	private List<String> tags;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastUpdateDate;
	private String userName;

}
