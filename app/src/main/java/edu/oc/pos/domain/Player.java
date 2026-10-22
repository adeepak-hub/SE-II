package edu.oc.pos.domain;

public class Player {
    private Long id;
    private Long teamId;
    private String name;
    private Integer jerseyNumber;

    public Player() {
    }

    public Player(Long id, Long teamId, String name, Integer jerseyNumber) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.jerseyNumber = jerseyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }
}
