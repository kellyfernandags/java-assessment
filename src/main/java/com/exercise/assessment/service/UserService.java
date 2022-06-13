package com.exercise.assessment.service;

import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;

import java.util.List;

public interface UserService {
    User findByIdOnApi(String id) throws NotFoundException;

    User saveOnRepository(User user);

    User findUserWithRoleById(String userId) throws NotFoundException;

    List<User> findAll();

    List<User> findByRole(Role role);

    void deleteById(String id);
}
