package edu.oc.pos.service;

import edu.oc.pos.domain.Player;
import edu.oc.pos.domain.PlayerRequest;
import edu.oc.pos.domain.Team;
import edu.oc.pos.domain.TeamRequest;
import edu.oc.pos.exception.DuplicateException;
import edu.oc.pos.exception.NotFoundException;
import edu.oc.pos.repository.PlayerRepository;
import edu.oc.pos.repository.TeamRepository;

import java.util.List;

public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Team createTeam(TeamRequest request) {
        requireName(request.getName());
        if (teamRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateException("A team named '" + request.getName() + "' already exists");
        }
        Team team = new Team(null, request.getName(), request.getHomeVenue(), request.getCoachName());
        return teamRepository.save(team);
    }

    public Team updateTeam(long teamId, TeamRequest request) {
        requireName(request.getName());
        Team existing = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found: " + teamId));
        teamRepository.findByName(request.getName())
                .filter(other -> !other.getId().equals(teamId))
                .ifPresent(other -> {
                    throw new DuplicateException("A team named '" + request.getName() + "' already exists");
                });
        existing.setName(request.getName());
        existing.setHomeVenue(request.getHomeVenue());
        existing.setCoachName(request.getCoachName());
        teamRepository.update(existing);
        return existing;
    }

    public Team getTeam(long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found: " + teamId));
    }

    public List<Team> listTeams() {
        return teamRepository.findAll();
    }

    public Player registerPlayer(PlayerRequest request) {
        if (request.getTeamId() == null) {
            throw new IllegalArgumentException("Team is required");
        }
        getTeam(request.getTeamId());
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Player name is required");
        }
        Player player = new Player(null, request.getTeamId(), request.getName().trim(), request.getJerseyNumber());
        return playerRepository.save(player);
    }

    public List<Player> listPlayers(long teamId) {
        getTeam(teamId);
        return playerRepository.findByTeamId(teamId);
    }

    private void requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Team name is required");
        }
    }
}
