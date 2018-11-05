package blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication habilita configurações padrão para a aplicação
 * springboot (procura e carrega entidades, controladores, UI templates, etc)
 */
@SpringBootApplication
public class BlogMvcApplication {

	public static void main(String[] args) {
		// Chama um tomcat embutido na porta 8080
		SpringApplication.run(BlogMvcApplication.class, args);
	}

}
