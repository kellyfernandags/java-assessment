package com.exercise.assessment.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.Team;
import com.exercise.assessment.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class MembershipRepositoryTest {

	@Autowired
	private MembershipRepository membershipRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Test
	public void shouldGetExistingMembershipFromRepository(){
		// start creating required data
		// User
		Role role = roleRepository.findById(1L).get();
		User user = new User("9965e622-c0c8-4d1c-b021-6f0aadacee54","hopeGislason",role);
		user = userRepository.save(user);
		// Team
		Team team = new Team("1e6da77f-dc6f-4994-b399-47626db9cd28","External Beige Parrotfish");
		team = teamRepository.save(team);
		// Membership
		Membership membership = new Membership(user, team);
		membership = membershipRepository.save(membership);
		
		MembershipForm form = new MembershipForm(team.getId(), user.getId());
		Optional<Membership> existingMembership = membershipRepository.findExistingMembership(form);
		
		Assertions.assertTrue(existingMembership.isPresent());
	}
	
	@Test
	public void shouldReturnEmptySearcMembershipFromRepository(){
		// start creating required data
		// User
		Role role = roleRepository.findById(2L).get();
		User user = new User("de5d1fd5-6bb6-41b9-b89b-505dc71ca4a3","johannaSpinka",role);
		user = userRepository.save(user);
		// Team
		Team team = new Team("4519d611-3f83-4272-b8aa-f3809f1b128a","Magic Peach Newt");
		team = teamRepository.save(team);
		
		MembershipForm form = new MembershipForm(team.getId(), user.getId());
		Optional<Membership> existingMembership = membershipRepository.findExistingMembership(form);
		
		Assertions.assertFalse(existingMembership.isPresent());
	}
		
	@Test
	public void shouldGetExistingMembershipFromRepositoryByRole(){
		Role role = roleRepository.findById(1L).get();
		List<Membership> existingMemberships = membershipRepository.findByRole(role);
		Assertions.assertTrue(existingMemberships.size()>0);
	}
}
