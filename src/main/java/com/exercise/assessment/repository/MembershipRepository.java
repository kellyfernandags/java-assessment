package com.exercise.assessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
	
	@Query("select mb from Membership mb where mb.user.id = :#{#form.userId} and mb.team.id = :#{#form.teamId}")
	Optional<Membership> findExistingMembership(@Param("form") MembershipForm form);

	@Query("select mb from Membership mb, User us where mb.user.id = us.id and us.role = :#{#role}")
	List<Membership> findByRole(@Param("role") Role role);

}
