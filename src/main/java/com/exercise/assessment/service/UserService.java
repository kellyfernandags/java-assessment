package com.exercise.assessment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public UserService() {
	}

	public Optional<User> findByIdOnApi(String id) {
		Optional<User> userOptional = Optional.empty();
		String uri = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/" + id;

		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForEntity(uri, User.class).getBody();
		if (user != null)
			userOptional = Optional.of(user);
		
		return userOptional;
	}

	public User saveAndFlushOnRepository(User user) {
		return this.userRepository.saveAndFlush(user);
	}

	public Optional<User> findUserWithRoleById(String userId) {
		return userRepository.findById(userId);
	}
}
