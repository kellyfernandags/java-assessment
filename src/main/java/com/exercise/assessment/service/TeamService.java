package com.exercise.assessment.service;

import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.model.Team;

public interface TeamService {
    Team findByIdOnApi(String id) throws NotFoundException;

    Team saveOnRepository(Team team);
}
