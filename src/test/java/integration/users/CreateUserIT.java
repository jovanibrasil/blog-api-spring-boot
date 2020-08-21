package integration.users;

import static io.restassured.RestAssured.given;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.blog.model.form.UserForm;

import integration.BaseTest;
import io.restassured.RestAssured;

public class CreateUserIT extends BaseTest {

	@Before
	public void beforeTest() {
		String token = "Bearer syJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtldXNlciIsInJvbGUiOiJST0xFX0FETUlOIiwiYXBwbmFtZSI6IkJMT0dfQVBQIiwiY3JlYXRlZCI6MTU5NTg5MTM2NTExMCwiZXhwIjoxNjU5MDA1MjY5fQ.Nrj_qVI_uMPNNPsyA51_VuRBNZ5Ha8PytBMR9J1iXVk";
		RestAssured.requestSpecification.header("Authorization", token); 
	}
	
	@Test
	public void createValidUser() {
		UserForm userForm = UserForm.builder()
			.userId(1L)
			.email("test@email.com")
			.userName(UUID.randomUUID().toString().substring(0, 11))
			.build();
		
		given()
			.body(userForm)
		.when()
			.post("/users")
		.then()
			.statusCode(201);
	}
	
}
