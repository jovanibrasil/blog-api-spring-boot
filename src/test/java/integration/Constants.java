package integration;

import io.restassured.http.ContentType;

public interface Constants {
	
	public final static String APP_BASE_URL = "http://127.0.0.1";
	public final static Integer APP_PORT = 8081;
	public final static String APP_BASE_PATH = "";
	
	public final static ContentType CONTENT_TYPE = ContentType.JSON;
	public final static Long MAX_TIMEOUT = 10000L;
	
}
