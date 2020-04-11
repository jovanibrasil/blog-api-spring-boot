package com.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Profile({ "dev", "prod" })
@Configuration
@EnableConfigurationProperties(BlogDataSourceProperties.class)
public class DataSourceConfig {

	private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
	
	private final BlogDataSourceProperties configuration;

	public DataSourceConfig(BlogDataSourceProperties configuration) {
		this.configuration = configuration;
	}

	@Bean
	public DataSource getDataResource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		log.info("Generatig datasource ...");
		
		dataSource.setUrl(configuration.getUrl()); //BLOG_MYSQL_URL
		dataSource.setUsername(configuration.getUsername()); //BLOG_MYSQL_USERNAME
		dataSource.setPassword(configuration.getPassword()); //BLOG_MYSQL_PASSWORD
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		return dataSource;
	}

}
