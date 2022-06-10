package com.exercise.assessment.service;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.repository.RoleRepository;
import com.exercise.assessment.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes={RoleRepository.class})
class RoleServiceTest {

    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.roleService = new RoleServiceImpl(this.roleRepository);
    }

    @Test
    void shouldReturnMembershipOnFindById() {
        Mockito.when(this.roleRepository.findById(Mockito.any(Long.class)))
                .thenReturn(this.getMockOptionalRole());
        Optional<Role> response = this.roleService.findById(1L);
        Assertions.assertTrue(response.isPresent());
    }

    @Test
    void shouldReturnListOfMembershipsOnFindAll() {
        Mockito.when(this.roleRepository.findAll())
                .thenReturn(this.getMockRoleList());
        List<Role> response = this.roleService.findAll();
        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    void save() {
        Mockito.when(this.roleRepository.save(Mockito.any(Role.class)))
                .thenReturn(this.getMockRole());
        Role response = this.roleService.save(new Role());
        assertNotNull(response);
    }

    @Test
    void deleteById() {
        this.roleService.deleteById(1L);
        Mockito.verify(this.roleRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnMembershipOnFindByName() {
        Mockito.when(this.roleRepository.findByName(Mockito.any(String.class)))
                .thenReturn(this.getMockOptionalRole());
        Optional<Role> response = this.roleService.findByName("test");
        Assertions.assertTrue(response.isPresent());
    }

    private Role getMockRole() {
        return new Role("Developer");
    }

    private Optional<Role> getMockOptionalRole() {
        return Optional.of(this.getMockRole());
    }

    private List<Role> getMockRoleList() {
        return Stream.of(this.getMockRole()).collect(Collectors.toList());
    }
}