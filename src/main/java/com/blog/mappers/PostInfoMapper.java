package com.blog.mappers;

import com.blog.dtos.PostInfoDTO;
import com.blog.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostInfoMapper {

    @Mapping(source = "postId", target = "id")
    PostInfoDTO postToPostInfoDto(Post post);

}
