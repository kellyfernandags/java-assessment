package com.exercise.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	List<User> findByRole(Role role);
	
}
