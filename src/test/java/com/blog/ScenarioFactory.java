package com.blog;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.blog.model.Post;
import com.blog.model.User;
import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostInfoDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.dto.UserDTO;
import com.blog.model.enums.ProfileTypeEnum;
import com.blog.model.mapper.PostInfoMapperImpl;
import com.blog.model.mapper.PostMapperImpl;
import com.blog.model.mapper.UserMapperImpl;

public class ScenarioFactory {

	public static User getUser() {
		return User.builder()
			.userName("jovanibrasil")
			.fullUserName("Jovani Brasil")
			.email("jovanibrasil@gmail.com")
			.githubUserName("jovanibrasil")
			.linkedinUserName("jovanibrasil")
			.phoneNumber("51999999999")
			.profileType(ProfileTypeEnum.ROLE_USER)
			.build();
	}

	public static UserDTO getUserDTO() {
		return (new UserMapperImpl()).userToUserDto(getUser());
	}	
	
	public static Post getPost0() {
		return Post.builder()
			.id(0L)
			.title("Post title")
			.body("Post body")
			.summary("Post summary")
			.lastUpdateDate(LocalDateTime.now())
			.creationDate(LocalDateTime.now())
			.tags(Arrays.asList("Java"))
			.author(getUser())
			.build();
	}

	public static PostDTO getPostDTO0() {
		return (new PostMapperImpl()).postToPostDto(getPost0());
	}
	
	public static Post getPost1() {
		return Post.builder()
			.id(1L)
			.title("Post1 title")
			.body("Post1 body")
			.summary("Post1 summary")
			.lastUpdateDate(LocalDateTime.now())
			.creationDate(LocalDateTime.now())
			.tags(Arrays.asList("Scala"))
			.author(getUser())
			.build();
	}
	
	public static PostDTO getPostDTO1() {
		return (new PostMapperImpl()).postToPostDto(getPost1());
	}

	public static PostInfoDTO getPostInfoDTO() {
		return (new PostInfoMapperImpl()).postToPostInfoDto(getPost0());
	}

	public static PostSummaryDTO getPostSummaryDTO0() {
		Post post = getPost0();
		return PostSummaryDTO.builder()
				.id(post.getId())
				.build();
	}

	public static PostSummaryDTO getPostSummaryDTO1() {
		Post post = getPost1();
		return PostSummaryDTO.builder()
				.id(post.getId())
				.build();
	}
	
}
