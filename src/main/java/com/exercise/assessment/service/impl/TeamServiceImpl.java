package com.exercise.assessment.service.impl;

import com.exercise.assessment.model.Team;
import com.exercise.assessment.repository.TeamRepository;
import com.exercise.assessment.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    TeamRepository teamRepository;
    private static final String BASE_URI = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/";

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Optional<Team> findByIdOnApi(String id) {
        Optional<Team> teamOptional = Optional.empty();
        String uri = BASE_URI + id;

        RestTemplate restTemplate = new RestTemplate();
        Team team = restTemplate.getForEntity(uri, Team.class).getBody();
        if (team != null)
            teamOptional = Optional.of(team);

        return teamOptional;
    }

    @Override
    public Team saveOnRepository(Team team) {
        return this.teamRepository.save(team);
    }
}
