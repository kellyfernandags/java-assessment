package com.exercise.assessment.repository;

import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.Team;
import com.exercise.assessment.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
class MembershipRepositoryTest {

	@Autowired
	private MembershipRepository membershipRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TeamRepository teamRepository;

	private Role role;
	private User user;
	private Team team;
	private Membership membership;

	@BeforeAll
	void setUp(){
		this.role = new Role("Business Partner");
		this.role = this.roleRepository.save(this.role);
		this.user = new User("9965e622-c0c8-4d1c-b021-6f0aadacee54","hopeGislason",this.role);
		this.user = this.userRepository.save(this.user);
		this.team = new Team("1e6da77f-dc6f-4994-b399-47626db9cd28","External Beige Parrotfish");
		this.team = this.teamRepository.save(this.team);
	}

	@Test
	@Order(1)
	void shouldReturnEmptySearchMembershipFromRepository(){
		MembershipForm form = new MembershipForm(this.team.getId(), this.user.getId());
		Optional<Membership> existingMembership = this.membershipRepository.findExistingMembership(form);
		Assertions.assertFalse(existingMembership.isPresent());
	}

	@Test
	@Order(2)
	void shouldSaveMembership(){
		membership = new Membership(this.user, this.team);
		membership = this.membershipRepository.save(membership);
		assertNotNull(membership);
	}

	@Test
	@Order(3)
	void shouldGetExistingMembershipFromRepository(){
		MembershipForm form = new MembershipForm(this.team.getId(), this.user.getId());
		Optional<Membership> existingMembership = this.membershipRepository.findExistingMembership(form);
		Assertions.assertTrue(existingMembership.isPresent());
	}

	@Test
	@Order(4)
	void shouldGetExistingMembershipFromRepositoryByRole(){
		List<Membership> existingMemberships = this.membershipRepository.findByRole(this.role);
		Assertions.assertTrue(existingMemberships.size()>0);
	}

	@AfterAll
	void tearDown() {
		this.membershipRepository.delete(this.membership);
		this.userRepository.delete(this.user);
		this.roleRepository.delete(this.role);
		this.teamRepository.delete(this.team);
	}
}
