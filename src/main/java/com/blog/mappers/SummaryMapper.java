package com.blog.mappers;

import com.blog.dtos.SummaryDTO;
import com.blog.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface SummaryMapper {

    @Mappings({
            @Mapping(source = "postId", target = "id"),
            @Mapping(source = "author.userName", target = "userName")
    })
    SummaryDTO postToSummaryDto(Post post);

}
