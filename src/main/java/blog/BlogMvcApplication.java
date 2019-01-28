package blog;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import blog.persistence.repositories.PostRepository;
import blog.persistence.repositories.UserRepository;
import blog.presentation.models.Post;
import blog.presentation.models.User;

/*
 * @SpringBootApplication habilita configurações padrão para a aplicação
 * springboot (procura e carrega entidades, controladores, UI templates, etc)
 */
@SpringBootApplication
//@EnableWebSecurity
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
			// System.out.println("Quantidade máxima de posts por usuário no plano free = " + this.maxPosts);

			String summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
					+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
					+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
					+ "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in"
					+ "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
					+ " Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia"
					+ "deserunt mollit anim id est laborum.";

			String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ipsum nisl, varius at semper a, consectetur at eros."
					+ "Nulla lectus quam, consequat nec cursus sit amet, lacinia et arcu. Duis tempus sodales venenatis. Orci varius natoque "
					+ "penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed ultrices odio nec neque placerat, in molestie "
					+ "orci lacinia. Proin eu neque at mauris lobortis mollis. Vestibulum in consectetur tellus. Maecenas suscipit, justo "
					+ "vitae maximus finibus, tortor turpis commodo ex, et varius nulla urna eu eros. Donec eget facilisis purus. Nunc"
					+ "tempor accumsan sem in vestibulum."
					+ "Sed ultrices mauris in lorem pellentesque, vel venenatis massa vestibulum. Praesent eu porttitor nunc. Vivamus quis "
					+ "imperdiet diam. Aliquam iaculis sed turpis vitae bibendum. Sed feugiat sodales eros, quis consectetur est sollicitudin non."
					+ "Nullam pretium felis vitae ex iaculis bibendum. Sed id lectus sodales, vulputate dolor id, vulputate risus. Maecenas lacinia, "
					+ "quam quis luctus tincidunt, mauris lectus tristique leo, id interdum leo lorem id ligula. In convallis commodo enim"
					+ "a convallis. Maecenas lorem quam, ultricies et nunc pharetra, consectetur laoreet ante. Morbi tempus quam ut urna "
					+ "tincidunt, tristique eleifend dolor lacinia. Sed ut gravida quam, nec efficitur nisi. Praesent nibh est, tristique "
					+ "vel gravida vitae, lacinia pharetra metus. Vestibulum aliquam pharetra odio at tristique. Curabitur id diam ligula."
					+ "In sodales aliquet est, nec viverra nisl ornare in. In bibendum lectus et sodales rhoncus. Etiam iaculis, felis lobortis"
					+ "iaculis faucibus, urna massa feugiat nisi, at porttitor nunc risus eu justo. Vestibulum nec dui orci. Morbi in ligula lacus."
					+ "Proin vitae est at lorem tristique molestie. Nunc in quam eu metus accumsan rhoncus vel vel sapien. Etiam suscipit pharetra"
					+ "massa. Curabitur congue felis a ullamcorper convallis. Morbi justo velit, tincidunt sit amet eros non, convallis viverra justo."
					+ "Maecenas mollis tempor mauris. Nulla pulvinar malesuada nisl at interdum. Sed sed tortor laoreet, rhoncus mauris ut, volutpat "
					+ "lacus. Praesent dictum tempor mi eu mattis. Aliquam orci augue, auctor vitae lacus in, tempus eleifend diam."
					+ "Cras suscipit scelerisque eros, at fermentum elit iaculis a. Nullam dolor lorem, hendrerit et accumsan at, dapibus sit "
					+ "amet enim. Sed vitae ante sed dui faucibus blandit. Nulla id interdum odio. Aenean rutrum augue tempor enim porta, eget "
					+ "condimentum nulla accumsan. Phasellus sed blandit justo, in molestie orci. Aenean ut tempus risus. Proin lacinia odio metus, "
					+ "sit amet dapibus ipsum congue ut. Etiam sollicitudin placerat nunc, nec tincidunt ante accumsan sed. Sed sodales dolor posuere"
					+ "nisl lacinia, et sodales metus finibus. Donec magna dolor, finibus ac fermentum in, tincidunt et nibh. Maecenas pellentesque"
					+ "dignissim risus a finibus."
					+ "Nunc fermentum, urna ac tempor semper, magna lorem elementum sem, a volutpat orci ex vitae erat. Integer id pulvinar neque. "
					+ "Quisque auctor, ligula in viverra bibendum, risus nulla tincidunt sapien, in sollicitudin nulla magna ut augue. Cras a tempus "
					+ "sapien, eu fringilla nisl. Curabitur metus ex, interdum vitae scelerisque nec, elementum id tellus. Praesent in est metus. Nunc "
					+ "semper sapien sed venenatis rhoncus. Aliquam vitae quam felis.";

			User user = new User();
			user.setFullUserName("Jovani Brasil");
			user.setUserName("jovanibrasil");
			//user.setLastUpdateDate(new Date());
			//user.setPasswordHash(new BCryptPasswordEncoder().encode("teste"));
			//user.setProfileType(ProfileTypeEnum.ROLE_USER);
			user.setPosts(new HashSet<Post>());

			user = userRepository.save(user);

			for (int i = 0; i < 20; i++) {
				Post post = new Post();
				post.setAuthor(user);
				post.setTitle("Lorem ipsum " + i);
				post.setBody(content);
				post.setLastUpdateDate(new Date());
				post = postRepository.save(post);
				System.out.println(post);
			}

		};
	}

}
