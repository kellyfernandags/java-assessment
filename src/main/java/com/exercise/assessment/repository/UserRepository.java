package com.exercise.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRole(Role role);

}
