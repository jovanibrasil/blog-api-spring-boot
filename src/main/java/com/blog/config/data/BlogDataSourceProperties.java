package com.blog.config.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter @Setter
@Profile({ "dev", "prod" })
@ConfigurationProperties("blog-db")
public class BlogDataSourceProperties {

	private String username;
	private String url;
	private String password;

}
