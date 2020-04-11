package com.blog.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Getter @Setter @NoArgsConstructor
@Profile("prod")
@ConfigurationProperties("blog-api")
public class BlogServiceProperties {

    private String username;
    private String password;

}
