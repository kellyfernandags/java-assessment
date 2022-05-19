package com.exercise.assessment.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.assessment.model.Team;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamServiceTest {

	@Autowired
	private TeamService teamService;

	@Test
	public void shouldGetValidTeamById() {
		Optional<Team> team = teamService.findByIdOnApi("7676a4bf-adfe-415c-941b-1739af07039b");
		Assertions.assertTrue(team.isPresent());
	}

	@Test
	public void shouldNotGetValidTeamById() {
		Optional<Team> team = teamService.findByIdOnApi("XXXXXXXXXXXXX");
		Assertions.assertFalse(team.isPresent());
	}
}
