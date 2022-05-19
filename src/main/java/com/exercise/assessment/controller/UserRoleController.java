package com.exercise.assessment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.RoleRepository;
import com.exercise.assessment.repository.UserRepository;

@RestController
@RequestMapping(value = "/users/role")
public class UserRoleController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	//Get all users that are assigned to a role (or to a specific role)
	@GetMapping
	public ResponseEntity<?> getUserAssignedToRolesList(@RequestParam(required = false) String roleId) {

		if (roleId == null) {
			List<User> users = userRepository.findAll();
			return ResponseEntity.ok(users);
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
			List<User> users = userRepository.findByRole(roleOpt.get());
			return ResponseEntity.ok(users);
		}
	}
}
