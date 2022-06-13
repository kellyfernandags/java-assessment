package com.exercise.assessment.service.impl;

import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.repository.RoleRepository;
import com.exercise.assessment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    private static final String DEFAULT_ROLE_NAME = "Developer";

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long roleId) throws NotFoundException {
        if (roleId == null)
            return this.roleRepository.findByName(DEFAULT_ROLE_NAME).orElseThrow (() ->
                    new NotFoundException("'" +roleId+ "' role default id is not found"));
        else
            return this.roleRepository.findById(roleId).orElseThrow (() ->
                    new NotFoundException("'" +roleId+ "' role id is not found"));
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        this.roleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
