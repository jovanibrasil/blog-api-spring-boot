package com.blog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.blog.dtos.PostDTO;
import com.blog.forms.PostForm;
import com.blog.models.Post;

@Mapper
public interface PostMapper {

    Post postFormToPost(PostForm postForm);
    
    @Mappings({
            @Mapping(source = "author.userName", target = "userName"),
            @Mapping(source = "banner.id", target = "bannerId")
    })
    PostDTO postToPostDto(Post post);
    
    Post postDtoToPost(PostDTO postDto);
    
}
