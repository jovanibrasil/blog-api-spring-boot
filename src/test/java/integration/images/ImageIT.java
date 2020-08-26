package integration.images;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.springframework.http.MediaType;

import integration.BaseTest;

public class ImageIT extends BaseTest {
	
	@Test
	public void getImageNotFound() {
		given()
		.when()
			.get("/images/{imageId}", 0L)
		.then()
			.statusCode(404);
	}

	@Test
	public void getImage() {
		given()
		.when()
			.get("/images/{imageId}", 1L)
		.then()
			.statusCode(200)
			.contentType(MediaType.IMAGE_JPEG_VALUE);
	}
}
