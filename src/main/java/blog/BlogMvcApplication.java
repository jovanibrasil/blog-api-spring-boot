package blog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/*
 * @SpringBootApplication habilita configurações padrão para a aplicação
 * springboot (procura e carrega entidades, controladores, UI templates, etc)
 */
@SpringBootApplication
@EnableWebSecurity
public class BlogMvcApplication {

	@Value("${posts.max-posts}")
	public int maxPosts;
	
	public static void main(String[] args) {
		// Chama um tomcat embutido na porta 8080
		SpringApplication.run(BlogMvcApplication.class, args);
	}
	
	// The result of this method is showed on the command line
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// Using value difined on application.properties
			System.out.println("Quantidade máxima de posts por "
					+ "usuário no plano free = " + this.maxPosts);
		};
	}

}
