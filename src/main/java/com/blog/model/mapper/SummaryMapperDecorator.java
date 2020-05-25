package com.blog.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.blog.model.Post;
import com.blog.model.dto.PostSummaryDTO;

public abstract class SummaryMapperDecorator implements SummaryMapper {

	private SummaryMapper summaryMapper;
	
	@Override
	public PostSummaryDTO postToSummaryDto(Post post) {
		PostSummaryDTO summaryDto = summaryMapper.postToSummaryDto(post);
		summaryDto.setBannerUrl("http://localhost:8081/api/images/" + post.getBanner().getId());
		return summaryDto;
	}	
	
	@Autowired
	public void setSummaryMapper(SummaryMapper summaryMapper) {
		this.summaryMapper = summaryMapper;
	}
	
}
