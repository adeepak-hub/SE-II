package edu.oc.pos;

import edu.oc.pos.db.Database;
import edu.oc.pos.repository.MatchRepository;
import edu.oc.pos.repository.PlayerRepository;
import edu.oc.pos.repository.TeamRepository;
import edu.oc.pos.service.MatchService;
import edu.oc.pos.service.TeamService;

public final class AppContext {
    private final TeamService teamService;
    private final MatchService matchService;

    public AppContext(Database database) {
        TeamRepository teamRepository = new TeamRepository(database);
        PlayerRepository playerRepository = new PlayerRepository(database);
        MatchRepository matchRepository = new MatchRepository(database);
        this.teamService = new TeamService(teamRepository, playerRepository);
        this.matchService = new MatchService(teamRepository, matchRepository);
    }

    public TeamService teamService() {
        return teamService;
    }

    public MatchService matchService() {
        return matchService;
    }
}
