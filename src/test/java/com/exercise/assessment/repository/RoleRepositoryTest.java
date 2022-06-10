package com.exercise.assessment.repository;

import com.exercise.assessment.model.Role;
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
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeAll
    void setUp() {
        this.role = new Role("Scrum Master");
    }

    @Test
    @Order(1)
    void shouldSaveRole(){
        this.role = this.roleRepository.save(this.role);
        assertNotNull(this.role);
    }

    @Test
    void shouldGetExistingRoleFromRepositoryByRoleName() {
        Optional<Role> existingRole = this.roleRepository.findByName(this.role.getName());
        Assertions.assertTrue(existingRole.isPresent());
    }

    @Test
    void shouldGetExistingRoleFromRepositoryByRoleId() {
        Optional<Role> existingRole = this.roleRepository.findById(this.role.getId());
        Assertions.assertTrue(existingRole.isPresent());
    }

    @AfterAll
    void tearDown() {
        this.roleRepository.delete(this.role);
    }
}