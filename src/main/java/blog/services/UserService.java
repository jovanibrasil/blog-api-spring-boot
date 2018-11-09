package blog.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import blog.models.User;

public interface UserService extends UserDetailsService {
	boolean authenticate(String username, String password);
	List<User> findAll();
	User findById(Long id);
	User create(User user);
	void deleteById(Long id);
	void saveUser(String userName, String fullUserName, String Password);
}
