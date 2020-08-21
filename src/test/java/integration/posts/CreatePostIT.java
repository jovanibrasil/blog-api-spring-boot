package integration.posts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.iterableWithSize;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.blog.model.form.PostForm;

import integration.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreatePostIT extends BaseTest {
	
	@Before
	public void beforeTest() {
		String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtldXNlciIsInJvbGUiOiJST0xFX0FETUlOIiwiYXBwbmFtZSI6IkJMT0dfQVBQIiwiY3JlYXRlZCI6MTU5NTg5MTM2NTExMCwiZXhwIjoxNjU5MDA1MjY5fQ.Nrj_qVI_uMPNNPsyA51_VuRBNZ5Ha8PytBMR9J1iXVk";
		RestAssured.requestSpecification.header("Authorization", token); 
	}
	
	@Test
	public void createValidPost() {		
		PostForm postForm = PostForm.builder()
				.title("Title")
				.body("Body")
				.summary("summary")
				.tags(Arrays.asList("Tag0", "Tag1"))
				.build();
				
		given()
			.multiPart("post", postForm)
			.multiPart("banner", new File("src/test/resources/ner.png"), "image/png")
			.header("content-type", "multipart/form-data")
		.when()
			.post("/posts")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("id", is(notNullValue()))
			.body("title", is(postForm.getTitle()))
			.body("body", is(postForm.getBody()))
			.body("summary", is(postForm.getSummary()))
			.body("likes", is(0))
			.body("bannerId", is(notNullValue()))
			.body("userName", is("fakeuser"))
			.body("creationDate", is(notNullValue()))
			.body("lastUpdateDate", is(notNullValue()))
			.body("tags", hasItems("Tag0", "Tag1"))
			.body("tags", iterableWithSize(2));
	}
		
	@Test
	public void createInvalidPostWithoutPostForm() {	
		given()
			.multiPart("banner", new File("src/test/resources/ner.png"), "image/png")
			.header("content-type", "multipart/form-data")
		.when()
			.post("/posts")
		.then()
			.statusCode(400)
			.contentType(ContentType.JSON)
			.body("errors.message", hasItem("Required request part 'post' is not present"));
	}
	
	@Test
	public void createInvalidPostWithoutBanner() {
		
		PostForm postForm = PostForm.builder()
				.title("Title")
				.body("Body")
				.summary("summary")
				.tags(Arrays.asList("Tag0", "Tag1"))
				.build();
				
		given()
			.multiPart("post", postForm)
			.header("content-type", "multipart/form-data")
		.when()
			.post("/posts")
		.then()
			.statusCode(400)
			.body("errors.message", hasItem("Required request part 'banner' is not present"));
	}
	
	@Test
	public void createInvalidPostWithoutData() {
				
		given()
			.header("content-type", "multipart/form-data")
		.when()
			.post("/posts")
		.then()
			.statusCode(400);
	}
	
	@Test
	public void createPostWithNullFields() {		
		PostForm postForm = PostForm.builder()
				.title(null)
				.body(null)
				.summary(null)
				.tags(null)
				.build();
				
		given()
			.multiPart("post", postForm)
			.multiPart("banner", new File("src/test/resources/ner.png"), "image/png")
			.header("content-type", "multipart/form-data")
		.when()
			.post("/posts")
		.then()
			.statusCode(400)
			.contentType(ContentType.JSON)
			.body("errors.message", hasItems(
				"Summary must not be null or blank.",
				"Body must not be null or blank.", 
				"Title must not be null or blank.", 
				"Tags list must not be null.")
			);
	}

}
