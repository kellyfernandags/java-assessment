package com.exercise.assessment.service;

import com.exercise.assessment.model.Team;
import com.exercise.assessment.repository.TeamRepository;
import com.exercise.assessment.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes={TeamRepository.class})
class TeamServiceTest {

	private TeamService teamService;

	@MockBean
	private TeamRepository teamRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.teamService = new TeamServiceImpl(this.teamRepository);
	}

	@Test
	void shouldGetValidTeamById() {
		Optional<Team> team = this.teamService.findByIdOnApi("7676a4bf-adfe-415c-941b-1739af07039b");
		Assertions.assertTrue(team.isPresent());
	}

	@Test
	void shouldNotGetValidTeamById() {
		Optional<Team> team = this.teamService.findByIdOnApi("XXXXXXXXXXXXX");
		Assertions.assertFalse(team.isPresent());
	}

}
