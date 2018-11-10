package blog.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import blog.models.User;
import blog.repositories.UserRepository;

@Service
@Primary
public class UserServiceJpaImpl implements UserService {
    
	@Autowired
	private UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
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

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findUserByName(userName);
		//System.out.println("-----------------_>" + user.getId() + "   " + user.getPasswordHash());
		return new CustomUserDetails(user.getId(), user.getFullUserName(), user.getPassword(),
				true, true, true, true, user.getAuthorities());
	}

	@Override
	public void saveUser(String userName, String fullUserName, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setFullUserName(fullUserName);
		user.setPasswordHash(passwordEncoder.encode(password));
		this.userRepository.save(user);
	}
	
	
}