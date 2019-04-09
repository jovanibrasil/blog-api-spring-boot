package com.blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/*
 * @SpringBootApplication habilita configurações padrão para a aplicação
 * springboot (procura e carrega entidades, controladores, UI templates, etc)
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

//	@Value("${posts.max-posts}")
//	public int maxPosts;

	public static void main(String[] args) {
		// Chama um tomcat embutido na porta 8080
		SpringApplication.run(Application.class, args);
	}

	// The result of this method is showed on the command line
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// ...
		};
	}

}
