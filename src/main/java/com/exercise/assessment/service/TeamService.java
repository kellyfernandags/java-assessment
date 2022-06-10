package com.exercise.assessment.service;

import com.exercise.assessment.model.Team;

import java.util.Optional;

public interface TeamService {
    Optional<Team> findByIdOnApi(String id);

    Team saveOnRepository(Team team);
}
