package com.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SummaryDTO {

	private Long id;
	private String title;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastUpdateDate;
	private String summary;
	private String userName;
	private List<String> tags;
	private String bannerUrl;

}
