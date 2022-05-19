package com.exercise.assessment.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.assessment.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void shouldGetValidUserById() {
		Optional<User> user = userService.findByIdOnApi("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c");
		Assertions.assertTrue(user.isPresent());
	}

	@Test
	public void shouldNotGetValidUserById() {
		Optional<User> user = userService.findByIdOnApi("XXXXXXXXXXXXX");
		Assertions.assertFalse(user.isPresent());
	}
}
