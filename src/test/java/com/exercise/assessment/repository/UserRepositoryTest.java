package com.exercise.assessment.repository;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private User user;
    private Role role;

    @BeforeAll
    void setUp() {
        this.role = new Role("Role to test user");
        this.role = this.roleRepository.save(role);
        this.user = new User("123344", "My New User", this.role);
    }

    @Test
    @Order(1)
    void shouldSaveUser() {
        this.user = this.userRepository.save(this.user);
        assertNotNull(this.user);
    }

    @Test
    void shouldGetExistingUserFromRepositoryByUserId() {
        Optional<User> existingUser = this.userRepository.findById(this.user.getId());
        Assertions.assertTrue(existingUser.isPresent());
    }

    @AfterAll
    void tearDown() {
        this.userRepository.delete(this.user);
        this.roleRepository.delete(this.role);
    }
}