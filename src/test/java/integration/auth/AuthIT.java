package integration.auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.blog.model.form.PostForm;
import com.blog.model.form.UserForm;

import integration.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthIT extends BaseTest {

	@Before
	public void setup() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
	}
	
	@Test
	public void createValidPostWithoutAuthentication() {
		
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
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));
	}
	
	@Test
	public void deletePostWithoutAuthentication() {
		
		given()
		.when()
			.delete("/posts/{postId}", 0)
		.then()
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));
	}
	
	@Test
	public void updatePostWithoutAuthentication() {
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
			.put("/posts/{postId}", 1)
		.then()
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));
	}

	@Test
	public void createValidUserWithoutAuthentication() {
		given()
			.body(new UserForm())
		.when()
			.post("/users")
		.then()
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));
	}
	
	@Test
	public void deleteUserWithoutAuthentication() {
		given()
		.when()
			.delete("/users/{userId}", 0)
		.then()
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));
	}
	
	@Test
	public void getUserWithoutAuthentication() {
		given()
		.when()
			.get("/users/{userId}", 0)
		.then()
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));
	}
	
	@Test
	public void getSubscriptionsWithoutToken() {
		given()
		.when()
			.get("/subscriptions")
		.then()
			.statusCode(401)
			.body("message", is("You need authentication to perform this operation."));	
	}
	
}
