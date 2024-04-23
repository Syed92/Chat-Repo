package com.chatroom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatroom.entity.User;
import com.chatroom.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User getUserById(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.orElse(null);
	}

	public User saveUser(User user) {
		log.info("saving user {}", user.getUsername());
		user = userRepository.save(user);

		return user;
	}

	public User getUserDetails(User user) {
		log.info("fetching user details {}", user.getUsername());
		user = userRepository.findByUsername(user.getUsername());
		return user;
	}

}
