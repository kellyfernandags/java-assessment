package com.exercise.assessment.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.assessment.form.RoleAssignmentForm;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.RoleRepository;
import com.exercise.assessment.service.UserService;

@RestController
@RequestMapping(value="/assignment")
public class RoleAssignmentController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService; 
	
	@PostMapping
	public ResponseEntity<?> addNewRoleAssignment(@RequestBody @Valid RoleAssignmentForm form) {
		// get user from external API/domain
		Optional<User> userOpt = userService.findByIdOnApi(form.getUserId());
		// get role that is already loaded to application
		Optional<Role> roleOpt = roleRepository.findById(form.getRoleId());
		
		if(!userOpt.isPresent())  { 
			String errors = String.format("'%s' is an invalid user", form.getUserId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
		if(!roleOpt.isPresent()) { 
			String errors = String.format("'%s' is an invalid role", form.getRoleId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
		
		User user = userOpt.get();
		Role role = roleOpt.get();
		user.setRole(role);
		
		User save = userService.saveAndFlushOnRepository(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
}
