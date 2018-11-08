package blog.services;

import org.springframework.stereotype.Service;

import blog.models.User;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceStubImpl implements UserService {
    
	public boolean authenticate(String username, String password) {
         // Provide a sample password check: username == password
         return Objects.equals(username, password);
    }

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
}