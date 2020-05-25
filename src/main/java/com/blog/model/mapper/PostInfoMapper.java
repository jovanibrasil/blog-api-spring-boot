package com.blog.model.mapper;

import org.mapstruct.Mapper;

import com.blog.model.Post;
import com.blog.model.dto.PostInfoDTO;

@Mapper
public interface PostInfoMapper {

    PostInfoDTO postToPostInfoDto(Post post);

}
