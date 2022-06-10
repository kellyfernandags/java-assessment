package com.exercise.assessment.service;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.UserRepository;
import com.exercise.assessment.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes={UserRepository.class})
class UserServiceTest {

	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.userService = new UserServiceImpl(this.userRepository);
	}

	@Test
	void shouldGetValidUserOnApiById() {
		Optional<User> user = this.userService.findByIdOnApi("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c");
		Assertions.assertTrue(user.isPresent());
	}

	@Test
	void shouldNotGetValidUserOnApiById() {
		Optional<User> user = this.userService.findByIdOnApi("XXXXXXXXXXXXX");
		Assertions.assertFalse(user.isPresent());
	}

	@Test
	void shouldSaveNewUser() {
		Mockito.when(this.userRepository.save(Mockito.any(User.class)))
				.thenReturn(this.getMockUser());
		User response = this.userService.saveOnRepository(new User());
		assertNotNull(response);
	}

	@Test
	void shouldReturnUserOnFindById() {
		Mockito.when(this.userRepository.findById(Mockito.any(String.class)))
				.thenReturn(this.getMockOptionalUser());
		Optional<User> response = this.userService.findById("test");
		Assertions.assertTrue(response.isPresent());
	}

	@Test
	void shouldReturnListOfUsersOnFindAll() {
		Mockito.when(this.userRepository.findAll())
				.thenReturn(this.getMockUserList());
		List<User> response = this.userService.findAll();
		Assertions.assertTrue(response.size() > 0);
	}

	@Test
	void shouldReturnUserOnFindByRole() {
		Mockito.when(this.userRepository.findByRole(Mockito.any(Role.class)))
				.thenReturn(this.getMockUserList());
		List<User> response = this.userService.findByRole(new Role());
		Assertions.assertTrue(response.size() > 0);
	}

	@Test
	void shouldDeleteMembershipById() {
		this.userRepository.deleteById("test");
		Mockito.verify(this.userRepository, Mockito.times(1)).deleteById("test");
	}

	private User getMockUser() {
		return new User("id-user-123", "userDisplayName", new Role("Developer"));
	}

	private Optional<User> getMockOptionalUser() {
		return Optional.of(this.getMockUser());
	}

	private List<User> getMockUserList() {
		return Stream.of(this.getMockUser()).collect(Collectors.toList());
	}
}
