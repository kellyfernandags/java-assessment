package com.exercise.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.assessment.model.Team;

public interface TeamRepository extends JpaRepository<Team, String>{

}
