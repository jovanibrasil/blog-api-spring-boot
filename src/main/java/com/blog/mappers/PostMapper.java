package com.blog.mappers;

import com.blog.dtos.PostDTO;
import com.blog.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface PostMapper {

    @Mapping(source = "id", target = "postId")
    Post postDtoToPost(PostDTO postDTO);
    @Mappings({
            @Mapping(source = "postId", target = "id"),
            @Mapping(source = "author.userName", target = "userName")
    })
    PostDTO postToPostDto(Post post);

}
