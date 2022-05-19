package com.exercise.assessment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercise.assessment.model.Team;
import com.exercise.assessment.repository.TeamRepository;

@Service
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	public TeamService() {
	}

	public Optional<Team> findByIdOnApi(String id) {
		Optional<Team> teamOptional = Optional.empty();
		String uri = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/" + id;

		RestTemplate restTemplate = new RestTemplate();
		Team team = restTemplate.getForEntity(uri, Team.class).getBody();
		if (team != null)
			teamOptional = Optional.of(team);
		
		return teamOptional;
	}

	public Team saveAndFlushOnRepository(Team team) {
		return this.teamRepository.saveAndFlush(team);		
	}
}
