package com.blog.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.Filter;

@Profile("dev")
@Configuration
public class FilterConfigs {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(someFilter());
        registration.addUrlPatterns("/blog-api/*");
        registration.setName("devFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "devFilter")
    public Filter someFilter() {
        return new DevFilter();
    }

}
