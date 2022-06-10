package com.exercise.assessment.repository;

import com.exercise.assessment.model.Team;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    private Team team;

    @BeforeAll
    void setUp() {
        this.team = new Team("123344", "My New Team");
    }

    @Test
    @Order(1)
    void shouldSaveTeam() {
        this.team = this.teamRepository.save(this.team);
        assertNotNull(this.team);
    }

    @Test
    void shouldGetExistingTeamFromRepositoryByTeamId() {
        Optional<Team> existingTeam = this.teamRepository.findById(this.team.getId());
        Assertions.assertTrue(existingTeam.isPresent());
    }

    @AfterAll
    void tearDown() {
        this.teamRepository.delete(this.team);
    }
}