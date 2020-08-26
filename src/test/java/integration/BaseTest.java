package integration;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class BaseTest implements Constants {

	@BeforeClass
	public static void baseSetup() {
		RestAssured.baseURI = APP_BASE_URL;
		RestAssured.port = APP_PORT;
		RestAssured.basePath = APP_BASE_PATH;
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(CONTENT_TYPE);
		RestAssured.requestSpecification = reqBuilder.build();
		
		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
		//respBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
		
		RestAssured.responseSpecification = respBuilder.build();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
}
