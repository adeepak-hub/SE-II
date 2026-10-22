package edu.oc.pos.service;

import edu.oc.pos.domain.Match;
import edu.oc.pos.domain.MatchRequest;
import edu.oc.pos.domain.MatchStatus;
import edu.oc.pos.exception.NotFoundException;
import edu.oc.pos.repository.MatchRepository;
import edu.oc.pos.repository.TeamRepository;

import java.util.List;
import java.util.Objects;

public class MatchService {
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public MatchService(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    public Match scheduleMatch(MatchRequest request) {
        if (request.getHomeTeamId() == null || request.getAwayTeamId() == null) {
            throw new NotFoundException("Home team and away team are required");
        }
        if (Objects.equals(request.getHomeTeamId(), request.getAwayTeamId())) {
            throw new IllegalArgumentException("A team cannot play against itself");
        }
        if (request.getMatchDate() == null) {
            throw new IllegalArgumentException("Match date/time is required");
        }
        teamRepository.findById(request.getHomeTeamId())
                .orElseThrow(() -> new NotFoundException("Team not found: " + request.getHomeTeamId()));
        teamRepository.findById(request.getAwayTeamId())
                .orElseThrow(() -> new NotFoundException("Team not found: " + request.getAwayTeamId()));

        Match match = new Match(
                null,
                request.getHomeTeamId(),
                request.getAwayTeamId(),
                request.getMatchDate(),
                MatchStatus.SCHEDULED
        );
        return matchRepository.save(match);
    }

    public List<Match> listMatches() {
        return matchRepository.findAll();
    }
}
