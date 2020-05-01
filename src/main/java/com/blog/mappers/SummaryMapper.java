package com.blog.mappers;

import com.blog.dtos.SummaryDTO;
import com.blog.models.Post;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
@DecoratedWith(SummaryMapperDecorator.class)
public interface SummaryMapper {

    @Mappings({
            @Mapping(source = "author.userName", target = "userName")
    })
    SummaryDTO postToSummaryDto(Post post);

}
