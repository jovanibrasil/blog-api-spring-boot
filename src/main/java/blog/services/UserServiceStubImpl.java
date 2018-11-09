package blog.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(String userName, String fullUserName, String Password) {
		// TODO Auto-generated method stub
		
	}
}