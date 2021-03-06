package com.exercise.assessment.service;

import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;

import java.util.List;
import java.util.Optional;

public interface MembershipService {

    Optional<Membership> findExistingMembership(MembershipForm form);

    Membership save(Membership membership);

    List<Membership> findAll();

    List<Membership> findByRole(Role role);

    Membership findById(Long id) throws NotFoundException;

    void deleteById(Long id);
}
