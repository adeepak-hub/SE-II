package edu.oc.pos.repository;

import edu.oc.pos.db.Database;
import edu.oc.pos.domain.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {
    private final Database database;

    public TeamRepository(Database database) {
        this.database = database;
    }

    public Team save(Team team) {
        String sql = "INSERT INTO teams (name, home_venue, coach_name) VALUES (?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, team.getName());
            statement.setString(2, team.getHomeVenue());
            statement.setString(3, team.getCoachName());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    team.setId(keys.getLong(1));
                }
            }
            return team;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save team", e);
        }
    }

    public void update(Team team) {
        String sql = "UPDATE teams SET name = ?, home_venue = ?, coach_name = ? WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, team.getName());
            statement.setString(2, team.getHomeVenue());
            statement.setString(3, team.getCoachName());
            statement.setLong(4, team.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update team", e);
        }
    }

    public Optional<Team> findById(long id) {
        String sql = "SELECT id, name, home_venue, coach_name FROM teams WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return Optional.of(mapTeam(results));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load team", e);
        }
    }

    public Optional<Team> findByName(String name) {
        String sql = "SELECT id, name, home_venue, coach_name FROM teams WHERE name = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return Optional.of(mapTeam(results));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load team by name", e);
        }
    }

    public List<Team> findAll() {
        String sql = "SELECT id, name, home_venue, coach_name FROM teams ORDER BY name";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            List<Team> teams = new ArrayList<>();
            while (results.next()) {
                teams.add(mapTeam(results));
            }
            return teams;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to list teams", e);
        }
    }

    private Team mapTeam(ResultSet results) throws SQLException {
        return new Team(
                results.getLong("id"),
                results.getString("name"),
                results.getString("home_venue"),
                results.getString("coach_name")
        );
    }
}
