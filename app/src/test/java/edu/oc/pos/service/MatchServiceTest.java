package edu.oc.pos.service;

/**
 * Feature 13 — Soccer League System
 * Spec: features/feature-13-soccer-league.md
 */

import edu.oc.pos.db.Database;
import edu.oc.pos.domain.Match;
import edu.oc.pos.domain.MatchRequest;
import edu.oc.pos.domain.MatchStatus;
import edu.oc.pos.domain.Team;
import edu.oc.pos.domain.TeamRequest;
import edu.oc.pos.repository.MatchRepository;
import edu.oc.pos.repository.PlayerRepository;
import edu.oc.pos.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchServiceTest {
    private TeamService teamService;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        Database database = Database.inMemory("match-" + UUID.randomUUID());
        TeamRepository teamRepository = new TeamRepository(database);
        teamService = new TeamService(teamRepository, new PlayerRepository(database));
        matchService = new MatchService(teamRepository, new MatchRepository(database));
    }

    @Test
    @DisplayName("Administrator schedules a valid match")
    void administratorSchedulesAValidMatch() {
        Team home = teamService.createTeam(new TeamRequest("Home United", "North Pitch", "Pat"));
        Team away = teamService.createTeam(new TeamRequest("Away Athletic", "South Pitch", "Sam"));
        LocalDateTime kickoff = LocalDateTime.of(2026, 11, 1, 15, 0);

        Match scheduled = matchService.scheduleMatch(new MatchRequest(home.getId(), away.getId(), kickoff));

        assertNotNull(scheduled.getId());
        assertEquals(home.getId(), scheduled.getHomeTeamId());
        assertEquals(away.getId(), scheduled.getAwayTeamId());
        assertEquals(kickoff, scheduled.getMatchDate());
        assertEquals(MatchStatus.SCHEDULED, scheduled.getStatus());

        List<Match> catalog = matchService.listMatches();
        assertEquals(1, catalog.size());
        assertEquals(scheduled.getId(), catalog.get(0).getId());
    }
}
