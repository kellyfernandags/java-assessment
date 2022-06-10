package com.exercise.assessment.service;

import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.Team;
import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.MembershipRepository;
import com.exercise.assessment.service.impl.MembershipServiceImpl;
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
@ContextConfiguration(classes={MembershipRepository.class})
class MembershipServiceTest {

    private MembershipService membershipService;

    @Mock
    private MembershipRepository membershipRepository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.membershipService = new MembershipServiceImpl(this.membershipRepository);
    }

    @Test
    void shouldReturnMembershipOnFindExistingMembership() {
        Mockito.when(this.membershipRepository.findExistingMembership(Mockito.any(MembershipForm.class)))
                .thenReturn(this.getMockOptionalMembership());
        Optional<Membership> response = this.membershipService.findExistingMembership(new MembershipForm());
        Assertions.assertTrue(response.isPresent());
    }

    @Test
    void shouldSaveNewMembership() {
        Mockito.when(this.membershipRepository.save(Mockito.any(Membership.class)))
                .thenReturn(this.getMockMembership());
        Membership response = this.membershipService.save(new Membership());
        assertNotNull(response);
    }

    @Test
    void shouldReturnListOfMembershipsOnFindAll() {
        Mockito.when(this.membershipRepository.findAll())
                .thenReturn(this.getMockMembershipList());
        List<Membership> response = this.membershipService.findAll();
        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    void shouldReturnListOfMembershipsOnFindByRole() {
        Mockito.when(this.membershipRepository.findByRole(Mockito.any(Role.class)))
                .thenReturn(this.getMockMembershipList());
        List<Membership> response = this.membershipService.findByRole(new Role());
        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    void shouldReturnMembershipOnFindById() {
        Mockito.when(this.membershipRepository.findById(Mockito.any(Long.class)))
                .thenReturn(this.getMockOptionalMembership());
        Optional<Membership> response = this.membershipService.findById(1L);
        Assertions.assertTrue(response.isPresent());
    }

    @Test
    void shouldDeleteMembershipById() {
        this.membershipService.deleteById(1L);
        Mockito.verify(this.membershipRepository, Mockito.times(1)).deleteById(1L);
    }

    private Membership getMockMembership() {
        return new Membership(new User("id-user-123", "userDisplayName", new Role("Developer")),
                new Team("id-team-123", "teamDisplayName"));
    }

    private Optional<Membership> getMockOptionalMembership() {
        return Optional.of(this.getMockMembership());
    }

    private List<Membership> getMockMembershipList() {
        return Stream.of(this.getMockMembership()).collect(Collectors.toList());
    }
}