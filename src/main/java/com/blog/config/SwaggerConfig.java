package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blog.config.security.AccessToken;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
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
				.description("Documentação de uma API para blogs. Este projeto está em desenvolvimento," +
						" qualquer problema ou dúvida entre em contato.").version("1.0")
				.contact(new Contact("Jovani Brasil", "jovanibrasil.com", "jovanibrasil@gmail.com"))
				.build();
	}
	
	/**
	 * Set security configuration
	 * @return
	 */
	public SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, 
			null, "Bearer " + AccessToken.getToken(), ApiKeyVehicle.HEADER,
			"Authorization", ",");
	}
	
}
