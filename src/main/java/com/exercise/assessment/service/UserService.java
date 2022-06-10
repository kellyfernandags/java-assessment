package com.exercise.assessment.service;

import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByIdOnApi(String id);

    User saveOnRepository(User user);

    Optional<User> findUserWithRoleById(String userId);

    List<User> findAll();

    List<User> findByRole(Role role);

    Optional<User> findById(String id);

    void deleteById(String id);
}
