package blog.business.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import blog.enums.ProfileTypeEnum;
import blog.persistence.repositories.UserRepository;
import blog.presentation.models.User;

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
	public Optional<User> findById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public User create(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void deleteById(Long id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findUserByName(userName);
		return new CustomUserDetails(user.getId(), user.getFullUserName(), user.getPassword(),
				true, true, true, true, user.getAuthorities());
	}

	@Override
	public void saveUser(Long userId, String userName, String fullUserName, String password) {
		User user = new User(userId, userName, fullUserName, ProfileTypeEnum.ROLE_USER);
		this.userRepository.save(user);
	}
	
}