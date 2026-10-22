package edu.oc.pos.domain;

public class TeamRequest {
    private final String name;
    private final String homeVenue;
    private final String coachName;

    public TeamRequest(String name, String homeVenue, String coachName) {
        this.name = name;
        this.homeVenue = homeVenue;
        this.coachName = coachName;
    }

    public String getName() {
        return name;
    }

    public String getHomeVenue() {
        return homeVenue;
    }

    public String getCoachName() {
        return coachName;
    }
}
