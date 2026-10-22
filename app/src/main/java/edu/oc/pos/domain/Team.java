package edu.oc.pos.domain;

public class Team {
    private Long id;
    private String name;
    private String homeVenue;
    private String coachName;

    public Team() {
    }

    public Team(Long id, String name, String homeVenue, String coachName) {
        this.id = id;
        this.name = name;
        this.homeVenue = homeVenue;
        this.coachName = coachName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeVenue() {
        return homeVenue;
    }

    public void setHomeVenue(String homeVenue) {
        this.homeVenue = homeVenue;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    @Override
    public String toString() {
        return name;
    }
}
