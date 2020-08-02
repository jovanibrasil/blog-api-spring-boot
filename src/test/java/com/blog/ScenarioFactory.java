package com.blog;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.blog.model.Image;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.dto.UserDTO;
import com.blog.model.enums.ProfileTypeEnum;
import com.blog.model.form.PostForm;
import com.blog.model.mapper.PostMapperImpl;
import com.blog.model.mapper.UserMapperImpl;

public class ScenarioFactory {

	public static User getUser() {
		return User.builder()
			.userName("username")
			.fullUserName("User Name")
			.creationDate(LocalDateTime.now())
			.lastUpdateDate(LocalDateTime.now())
			.email("username@gmail.com")
			.githubUserName("username")
			.linkedinUserName("username")
			.phoneNumber("51999999999")
			.profileType(ProfileTypeEnum.ROLE_USER)
			.build();
	}

	public static UserDTO getUserDTO() {
		return (new UserMapperImpl()).userToUserDto(getUser());
	}	
	
	public static Post getPostJava() {
		return Post.builder()
			.id(0L)
			.title("Java Title")
			.body("Java Body")
			.banner(new Image())
			.summary("Java Summary")
			.lastUpdateDate(LocalDateTime.now())
			.creationDate(LocalDateTime.now())
			.tags(Arrays.asList("Java"))
			.author(getUser())
			.build();
	}

	public static PostDTO getPostJavaDTO() {
		return (new PostMapperImpl()).postToPostDto(getPostJava());
	}
	
	public static Post getPostSpring() {
		return Post.builder()
			.id(1L)
			.title("Spring Title")
			.body("Spring Body")
			.banner(new Image())
			.summary("Spring Summary")
			.lastUpdateDate(LocalDateTime.now())
			.creationDate(LocalDateTime.now())
			.tags(Arrays.asList("Java", "Spring"))
			.author(getUser())
			.build();
	}
	
	public static PostDTO getPostDTO1() {
		return (new PostMapperImpl()).postToPostDto(getPostSpring());
	}

	public static PostSummaryDTO getPostSummaryDTO0() {
		Post post = getPostJava();
		return PostSummaryDTO.builder()
				.id(post.getId())
				.build();
	}

	public static PostForm getPostJavaForm() {
		Post post = getPostJava();
		return PostForm.builder()
				.title(post.getTitle())
				.summary(post.getSummary())
				.body(post.getBody())
				.tags(post.getTags())
				.creationDate(post.getCreationDate())
				.lastUpdateDate(post.getLastUpdateDate())
				.build();
	}

	public static PostSummaryDTO getPostSummaryDTO1() {
		Post post = getPostSpring();
		return PostSummaryDTO.builder()
				.id(post.getId())
				.build();
	}
	
	public static Image getCompressedImage() {
		byte[] compressedImageExpected = { 120, -100, 51, 48, 52, 48, 52, 64, 1, -122, 0, 35, -42, 3, -108 };
		MultipartFile decompressedMtf = new MockMultipartFile("img", "img.jpg",
				MediaType.IMAGE_JPEG_VALUE, compressedImageExpected);
		return new Image(decompressedMtf);
	}
	
	public static Image getDecompressedImage() {
		MultipartFile decompressedMtf = new MockMultipartFile("img", "img.jpg",
				MediaType.IMAGE_JPEG_VALUE, "0101000000000000001".getBytes());
		return new Image(decompressedMtf);
	}

}
