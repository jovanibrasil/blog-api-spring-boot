package com.blog.mappers;

import com.blog.dtos.PostInfoDTO;
import com.blog.models.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostInfoMapper {

    PostInfoDTO postToPostInfoDto(Post post);

}
