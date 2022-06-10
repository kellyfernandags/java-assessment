package com.exercise.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.assessment.model.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

}
