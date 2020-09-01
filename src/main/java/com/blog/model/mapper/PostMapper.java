package com.blog.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.blog.model.Post;
import com.blog.model.dto.PostDTO;
import com.blog.model.form.PostForm;

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
