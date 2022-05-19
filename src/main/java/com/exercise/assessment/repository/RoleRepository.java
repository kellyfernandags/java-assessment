package com.exercise.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.assessment.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);

}
