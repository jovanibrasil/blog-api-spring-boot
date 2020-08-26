package integration.posts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.blog.model.form.PostForm;

import integration.BaseTest;
import io.restassured.RestAssured;

public class DeletePostIT extends BaseTest {

	private int postId;
	
	@Before
	public void beforeTest() {
		
		String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtldXNlciIsInJvbGUiOiJST0xFX0FETUlOIiwiYXBwbmFtZSI6IkJMT0dfQVBQIiwiY3JlYXRlZCI6MTU5NTg5MTM2NTExMCwiZXhwIjoxNjU5MDA1MjY5fQ.Nrj_qVI_uMPNNPsyA51_VuRBNZ5Ha8PytBMR9J1iXVk";
		RestAssured.requestSpecification.header("Authorization", token); 
	
		PostForm postForm = PostForm.builder()
				.title("Title")
				.body("Body")
				.summary("summary")
				.tags(Arrays.asList("Tag0", "Tag1"))
				.build();
				
		postId = given()
			.multiPart("post", postForm)
			.multiPart("banner", new File("src/test/resources/ner.png"), "image/png")
			.header("content-type", "multipart/form-data")
		.when()
			.post("/posts")
		.then()
			.extract().path("id");
	}
	
	@Test
	public void deleteNotExistentPost() {		
		given()
		.when()
			.delete("/posts/{postId}", 0)
		.then()
			.statusCode(404)
			.body("errors.message", hasItem("It was not possible to find the specified post."));
	}
	
	@Test
	public void deleteExistentPost() {		
		given()
		.when()
			.delete("/posts/{postId}", postId)
		.then()
			.statusCode(204);
	}
	
}
