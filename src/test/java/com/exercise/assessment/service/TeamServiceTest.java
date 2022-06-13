package com.exercise.assessment.service;

import com.exercise.assessment.exception.NotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = {TeamRepository.class})
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
    void shouldGetValidTeamById() throws NotFoundException {
        Team team = this.teamService.findByIdOnApi("7676a4bf-adfe-415c-941b-1739af07039b");
        Assertions.assertNotNull(team);
    }

    @Test
    void shouldNotGetValidTeamById() {
        Exception exception = assertThrows(NotFoundException.class, () -> this.teamService.findByIdOnApi("XXXXXXXXXXXXX"));
        assertNotNull(exception);
    }
}
