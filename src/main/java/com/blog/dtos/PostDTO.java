package com.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class PostDTO {

	@NotNull
	private Long id;
	
	@NotBlank(message="{error.post.title.notblank}")
	@Size(min=2, max=50, message="{error.post.title.size}")
	private String title;
	
	@NotBlank(message="{error.post.summary.notblank}")
	@Size(min=2, max=5000, message="{error.post.summary.size}")
	private String summary;

	@NotBlank(message="{error.post.body.notblank}")
	@Size(min=2, max=30000, message="{error.post.body.size}")
	private String body;
	
	@NotNull(message="{error.post.tags.notnull}")
	private List<String> tags;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastUpdateDate;
	private String userName;

}
