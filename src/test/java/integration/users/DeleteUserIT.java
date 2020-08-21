package integration.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.Before;
import org.junit.Test;

import com.blog.model.form.UserForm;

import integration.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class DeleteUserIT extends BaseTest {
	
	private UserForm userForm;
	
	@Before
	public void beforeTest() {
		String token = "Bearer syJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtldXNlciIsInJvbGUiOiJST0xFX0FETUlOIiwiYXBwbmFtZSI6IkJMT0dfQVBQIiwiY3JlYXRlZCI6MTU5NTg5MTM2NTExMCwiZXhwIjoxNjU5MDA1MjY5fQ.Nrj_qVI_uMPNNPsyA51_VuRBNZ5Ha8PytBMR9J1iXVk";
		RestAssured.requestSpecification.header("Authorization", token); 
	
		userForm = UserForm.builder()
				.userId(1L)
				.email("test@email.com")
				.userName("test")
				.build();
			
		given().body(userForm).when().post("/users"); 
	}
	
	@Test
	public void deleteExistentUser() {
		given()
		.when()
			.delete("/users/{userName}", userForm.getUserName())
		.then()
			.statusCode(204);
	}
	
	@Test
	public void deleteNotExistentUser() {
		given()
		.when()
			.delete("/users/{userName}", "unknownUser")
		.then()
			.statusCode(404)
			.contentType(ContentType.JSON)
			.body("errors.message", hasItem("It was not possible to find the specified user."));
	}

}
