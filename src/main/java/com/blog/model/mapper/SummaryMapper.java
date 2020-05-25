package com.blog.model.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.blog.model.Post;
import com.blog.model.dto.PostSummaryDTO;

@Mapper
@DecoratedWith(SummaryMapperDecorator.class)
public interface SummaryMapper {

    @Mappings({
            @Mapping(source = "author.userName", target = "userName")
    })
    PostSummaryDTO postToSummaryDto(Post post);

}
