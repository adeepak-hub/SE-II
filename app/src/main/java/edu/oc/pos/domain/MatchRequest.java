package edu.oc.pos.domain;

import java.time.LocalDateTime;

public class MatchRequest {
    private final Long homeTeamId;
    private final Long awayTeamId;
    private final LocalDateTime matchDate;

    public MatchRequest(Long homeTeamId, Long awayTeamId, LocalDateTime matchDate) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.matchDate = matchDate;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }
}
