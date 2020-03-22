package com.blog.services.impl;

import com.blog.enums.ProfileTypeEnum;
import com.blog.exceptions.NotFoundException;
import com.blog.exceptions.UserException;
import com.blog.models.User;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User findByUserName(String userName) {
		Optional<User> optUser = this.userRepository.findByName(userName);
		if(!optUser.isPresent()) {
			log.error("It was not possible to find the specified user.");
			throw new NotFoundException("error.user.find");
		}
		return optUser.get();
	}

	@Override
	public User save(User user) {
		Optional<User> optUser = this.userRepository.findByName(user.getUserName());
		if(optUser.isPresent()){
			log.error("It was not possible to create the specified user. Username already exists.");
			throw new UserException("error.user.name.unique");
		}
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		return userRepository.save(user);	
	}

	@Override
	public void deleteByUserName(String userName) {
		User user = findByUserName(userName);
		userRepository.delete(user);
	}
	
}