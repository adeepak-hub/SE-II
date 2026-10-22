package edu.oc.pos.domain;

public class PlayerRequest {
    private final Long teamId;
    private final String name;
    private final Integer jerseyNumber;

    public PlayerRequest(Long teamId, String name, Integer jerseyNumber) {
        this.teamId = teamId;
        this.name = name;
        this.jerseyNumber = jerseyNumber;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }
}
