package com.exercise.assessment.service.impl;

import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.repository.MembershipRepository;
import com.exercise.assessment.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    MembershipRepository membershipRepository;

    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Optional<Membership> findExistingMembership(MembershipForm form) {
        return this.membershipRepository.findExistingMembership(form);
    }

    @Override
    public Membership save(Membership membership) {
        return this.membershipRepository.save(membership);
    }

    @Override
    public List<Membership> findAll() {
        return this.membershipRepository.findAll();
    }

    @Override
    public List<Membership> findByRole(Role role) {
        return this.membershipRepository.findByRole(role);
    }

    @Override
    public Membership findById(Long id) throws NotFoundException {
        return this.membershipRepository.findById(id).orElseThrow (() ->
                new NotFoundException("'" + id + "' membership id is not found"));
    }

    @Override
    public void deleteById(Long id) {
        this.membershipRepository.deleteById(id);
    }
}
