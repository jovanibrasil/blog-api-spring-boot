package com.blog.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter @Setter
@NoArgsConstructor
@Profile("prod")
@ConfigurationProperties("blog-db-cred")
public class BlogDataSourceProperties {

	private String username;
	private String url;
	private String password;

}
