package blog;

import java.awt.Point;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.PostRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import blog.business.services.PostService;
import blog.business.services.UserService;
import blog.enums.ProfileTypeEnum;
import blog.persistence.repositories.PostRepository;
import blog.persistence.repositories.UserRepository;
import blog.presentation.models.Post;
import blog.presentation.models.User;

/*
 * @SpringBootApplication habilita configurações padrão para a aplicação
 * springboot (procura e carrega entidades, controladores, UI templates, etc)
 */
@SpringBootApplication
@EnableWebSecurity
public class BlogMvcApplication {

//	@Value("${posts.max-posts}")
//	public int maxPosts;
//	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	public static void main(String[] args) {
		// Chama um tomcat embutido na porta 8080
		SpringApplication.run(BlogMvcApplication.class, args);
	}
	
	// The result of this method is showed on the command line
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			// Using value difined on application.properties
//			System.out.println("Quantidade máxima de posts por "
//					+ "usuário no plano free = " + this.maxPosts);
			
			User user = new User();
			user.setFullUserName("Jovani Brasil");
			user.setUserName("jovanibrasil");
			user.setLastUpdateDate(new Date());
			user.setPasswordHash(new BCryptPasswordEncoder().encode("teste"));
			user.setProfileType(ProfileTypeEnum.ROLE_USER);
			user.setPosts(new HashSet<Post>());
		
			userRepository.save(user);
			
			for (int i = 0; i < 20; i++) {
				Post post = new Post();
				post.setAuthor(user);
				post.setTitle("Título");
				post.setBody("Conteúdo do post.");
				post.setLastUpdateDate(new Date());
				postRepository.save(post);
			}
			
		};
	}

}
