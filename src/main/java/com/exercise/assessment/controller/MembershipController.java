package com.exercise.assessment.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.Team;
import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.MembershipRepository;
import com.exercise.assessment.repository.RoleRepository;
import com.exercise.assessment.service.TeamService;
import com.exercise.assessment.service.UserService;

@RestController
@RequestMapping(value="/memberships")
public class MembershipController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private UserService userService;

	@Autowired
	private MembershipRepository membershipRepository; 
	
	@Autowired
	private RoleRepository roleRepository;

	@PostMapping
	public ResponseEntity<?> addNewMembership(@RequestBody @Valid MembershipForm form) {
		//check if membership exists for this tuple before save and throw error if yes
		Optional<Membership> membershipOpt = membershipRepository.findExistingMembership(form);
		if(membershipOpt.isPresent()){ 
			String errors = String.format("'%s' already exists in the system", form);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
		}
		// get record from local database, so they are already assigned to role
		Optional<User> userOpt = userService.findUserWithRoleById(form.getUserId());
		// get team from external API/domain
		Optional<Team> teamOpt = teamService.findByIdOnApi(form.getTeamId());
		
		if(!userOpt.isPresent()) { 
			String errors = String.format("'%s' is an invalid user or is not assigned to a role yet", form.getUserId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
		if(!teamOpt.isPresent()){ 
			String errors = String.format("'%s' is an invalid team", form.getTeamId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
		
		User user = userOpt.get();
		Team team = teamOpt.get();
		
		// save record from existing API locally
		teamService.saveAndFlushOnRepository(team);
		
		Membership membership = new Membership(user,team);
		Membership save = membershipRepository.save(membership);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@GetMapping
	public ResponseEntity<?> getMembershipsList(@RequestParam(required = false) String roleId) {

		if (roleId == null) {
			List<Membership> memberships = membershipRepository.findAll();
			return ResponseEntity.ok(memberships);
		} 
		else {
			long longRoleId;
			try {
				longRoleId = Long.valueOf(roleId);
			} catch (NumberFormatException e) {
				String errors = String.format("'%s' is an invalid 'roleId'. 'roleId' should be a valid number", roleId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
			}

			Optional<Role> roleOpt = roleRepository.findById(longRoleId);
			if (!roleOpt.isPresent()) {
				String errors = String.format("'%s' id is not related to a valid role", roleId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
			}
			List<Membership> memberships = membershipRepository.findByRole(roleOpt.get());
			return ResponseEntity.ok(memberships);
		}
	}
}
