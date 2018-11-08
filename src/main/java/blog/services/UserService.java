package blog.services;

import java.util.List;

import blog.models.User;

public interface UserService {
	boolean authenticate(String username, String password);
	List<User> findAll();
	User findById(Long id);
	User create(User user);
	void deleteById(Long id);
}
