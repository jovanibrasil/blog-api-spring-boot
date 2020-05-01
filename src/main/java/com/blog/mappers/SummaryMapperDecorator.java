package com.blog.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import com.blog.dtos.SummaryDTO;
import com.blog.models.Post;

public abstract class SummaryMapperDecorator implements SummaryMapper {

	private SummaryMapper summaryMapper;
	
	@Override
	public SummaryDTO postToSummaryDto(Post post) {
		SummaryDTO summaryDto = summaryMapper.postToSummaryDto(post);
		summaryDto.setBannerUrl("http://localhost:8081/api/images/" + post.getBanner().getId());
		return summaryDto;
	}	
	
	@Autowired
	public void setSummaryMapper(SummaryMapper summaryMapper) {
		this.summaryMapper = summaryMapper;
	}
	
}
