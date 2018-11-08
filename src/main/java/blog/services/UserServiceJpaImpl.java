package blog.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import blog.models.User;
import blog.repositories.UserRepository;

@Service
@Primary
public class UserServiceJpaImpl implements UserService {
    
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean authenticate(String username, String password) {
        // Provide a sample password check: username == password
        return Objects.equals(username, password);
	}
	
	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return this.userRepository.findOne(id);
	}

	@Override
	public User create(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void deleteById(Long id) {
		this.userRepository.delete(id);
	}
}