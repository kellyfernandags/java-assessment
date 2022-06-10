package com.exercise.assessment.service;

import com.exercise.assessment.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long roleId);

    List<Role> findAll();

    Role save(Role role);

    void deleteById(Long id);

    Optional<Role> findByName(String name);
}
