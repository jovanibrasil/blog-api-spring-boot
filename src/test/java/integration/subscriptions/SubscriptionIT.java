package integration.subscriptions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.blog.model.dto.SubscriptionForm;

import integration.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class SubscriptionIT extends BaseTest {

	@Before
	public void setup() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
	}
	
	@Test
	public void subscribeWithValidEmail() {
		SubscriptionForm subsDTO = new SubscriptionForm(UUID.randomUUID().toString()+"@email");
		given()
			.body(subsDTO)
		.when()
			.post("/subscriptions")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void subscribeWithInvalidEmail() {
		SubscriptionForm subsDTO = new SubscriptionForm("email");
		given()
			.body(subsDTO)
		.when()
			.post("/subscriptions")
		.then()
			.statusCode(400)
			.body("errors.message", hasItem("Invalid email format."));
	}
	
}
