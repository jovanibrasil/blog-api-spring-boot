package com.blog;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.blog.enums.ProfileTypeEnum;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;

/*
 * @SpringBootApplication habilita configurações padrão para a aplicação
 * springboot (procura e carrega entidades, controladores, UI templates, etc)
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

//	@Value("${posts.max-posts}")
//	public int maxPosts;
//	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	public static void main(String[] args) {
		// Chama um tomcat embutido na porta 8080
		SpringApplication.run(Application.class, args);
	}

	// The result of this method is showed on the command line
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// Using value difined on application.properties
			// System.out.println("Quantidade máxima de posts por usuário no plano free = " + this.maxPosts);
			
//			String title[] = { "Configurando meu blog", "Desenvolvendo um blog", "Escrevendo posts",
//					"Testando um blog", "Vivendo um blog"}; 
//			
//			String summary[] = { "Apenda a configurar seu blog", "Descobrindo como desenvolver um blog",
//					"Como escrever um bom post", "Métodos e técnicas para testar im blog", "Viva o conhecimento"};
//			
//			String content[] = {
//					"Configurar um blog é uma tarefa que pode ser complexa, dependendo de que tipo de blog você tem	e qual é o seu público.",
//					"Existem várias tecnologias disponíveis para desenvolvimento de um blog, cabe a você decidir qual é a melhor para você.",
//					"Escrever um post não é fácil como se imagina, depende do seu conhecimento técnico dos recursos disponíveis.",
//					"Quando falo em testar um blog estou me referindo ao mais importante: a percepção do usuário.",
//					"O conhecimento é a única coisa que você leva para a vida toda e pode te trazer sucesso, riqueza e felicidade."
//			};
//
//			String tags[] = { "angular", "docker", "java", "others", "ai"};
//			
//			User user1 = new User();
//			user1.setFullUserName("Jovani Brasil");
//			user1.setUserName("jovanibrasil");
//	
//			//user.setLastUpdateDate(new Date());
//			//user.setPasswordHash(new BCryptPasswordEncoder().encode("teste"));
//			user1.setProfileType(ProfileTypeEnum.ROLE_USER);
//			user1.setPosts(new HashSet<Post>());
//
//			user1 = userRepository.save(user1);
//			
//			User user2 = new User();
//			user2.setFullUserName("Fake user");
//			user2.setUserName("fakeuser");
//			//user.setLastUpdateDate(new Date());
//			//user.setPasswordHash(new BCryptPasswordEncoder().encode("teste"));
//			user2.setProfileType(ProfileTypeEnum.ROLE_USER);
//			user2.setPosts(new HashSet<Post>());
//
//			user2 = userRepository.save(user2);
//			
//			for (int i = 0; i < 5; i++) {
//				Post post = new Post();
//				
//				if(i%2 == 0) {
//					post.setAuthor(user1);
//				}
//				else {
//					post.setAuthor(user2);
//				}
//					
//				post.setTitle(title[i]);
//				post.setSummary(summary[i]);
//				post.setBody(content[i]);
//				post.setCreationDate(new Date());
//				post.setLastUpdateDate(new Date());
//				post.addTag(tags[i]);
//				post = postRepository.save(post);
//				
//			}

		};
	}

}
