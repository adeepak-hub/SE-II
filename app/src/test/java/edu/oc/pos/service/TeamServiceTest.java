package edu.oc.pos.service;

/**
 * Feature 13 — Soccer League System
 * Spec: features/feature-13-soccer-league.md
 */

import edu.oc.pos.db.Database;
import edu.oc.pos.domain.Player;
import edu.oc.pos.domain.PlayerRequest;
import edu.oc.pos.domain.Team;
import edu.oc.pos.domain.TeamRequest;
import edu.oc.pos.repository.PlayerRepository;
import edu.oc.pos.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamServiceTest {
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        Database database = Database.inMemory("team-" + UUID.randomUUID());
        teamService = new TeamService(new TeamRepository(database), new PlayerRepository(database));
    }

    @Test
    @DisplayName("Administrator registers a new team successfully")
    void administratorRegistersANewTeamSuccessfully() {
        TeamRequest request = new TeamRequest("River FC", "Riverside Stadium", "Alex Coach");

        Team saved = teamService.createTeam(request);
        Player player = teamService.registerPlayer(new PlayerRequest(saved.getId(), "Jordan Striker", 9));

        assertNotNull(saved.getId());
        Team persisted = teamService.getTeam(saved.getId());
        assertEquals("River FC", persisted.getName());
        assertEquals("Riverside Stadium", persisted.getHomeVenue());
        assertEquals("Alex Coach", persisted.getCoachName());

        List<Team> activeTeams = teamService.listTeams();
        assertTrue(activeTeams.stream().anyMatch(team -> "River FC".equals(team.getName())));
        assertEquals("Jordan Striker", player.getName());
        assertEquals(1, teamService.listPlayers(saved.getId()).size());
    }
}
