package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.blog.integrations.AuthClient;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("prod")
@EnableSwagger2
public class SwaggerConfig {

	@Value("${blog.admin-username}")
	private String adminUserName;
	
	@Value("${blog.admin-password}")
	private String adminPassword;
	
	@Autowired
	AuthClient authClient;
	
	/*
	 * Swagger configuration
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.blog.controllers"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}
	
	/**
	 * Custom information
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Blog API")
				.description("Blog documentation").version("1.0")
				.build();
	}
	
	/**
	 * Set security configuration
	 * @return
	 */
	public SecurityConfiguration security() {
		String token;
		try {
			token = authClient.getToken(adminUserName, adminPassword);
		} catch (Exception e) {
			token = "";
		}
		return new SecurityConfiguration(null, null, null, 
			null, "Bearer " + token, ApiKeyVehicle.HEADER,
			"Authorization", ",");
	}
	
}
