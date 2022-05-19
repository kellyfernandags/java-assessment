package com.exercise.assessment.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.assessment.form.RoleForm;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.repository.RoleRepository;

@RestController
@RequestMapping(value="/roles")
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping
	public ResponseEntity<List<Role>> getRolesList(){
		List<Role> roles = roleRepository.findAll();
		return ResponseEntity.ok(roles);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
		Optional<Role> role = roleRepository.findById(id);
		if(role.isPresent())
			return ResponseEntity.ok(role.get());
		else 
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Role> addNewRole(@RequestBody @Valid RoleForm form) {
		Role role = form.convert();
		Role save = roleRepository.save(role);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Role> deleteRoleById(@PathVariable Long id) {
		Optional<Role> role = roleRepository.findById(id);
		if(role.isPresent()) {
			roleRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else 
			return ResponseEntity.notFound().build();
	}

}
