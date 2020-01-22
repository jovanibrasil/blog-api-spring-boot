package com.blog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter @Setter
@Profile("prod")
@ConfigurationProperties("blog-db-cred")
public class BlogDataSourceProperties {

	private String username;
	private String url;
	private String password;

}
