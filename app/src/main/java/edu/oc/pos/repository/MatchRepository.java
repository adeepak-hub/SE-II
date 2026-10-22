package edu.oc.pos.repository;

import edu.oc.pos.db.Database;
import edu.oc.pos.domain.Match;
import edu.oc.pos.domain.MatchStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MatchRepository {
    private final Database database;

    public MatchRepository(Database database) {
        this.database = database;
    }

    public Match save(Match match) {
        String sql = "INSERT INTO matches (home_team_id, away_team_id, match_date) VALUES (?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, match.getHomeTeamId());
            statement.setLong(2, match.getAwayTeamId());
            statement.setTimestamp(3, Timestamp.valueOf(match.getMatchDate()));
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    match.setId(keys.getLong(1));
                }
            }
            return match;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save match", e);
        }
    }

    public List<Match> findAll() {
        String sql = "SELECT id, home_team_id, away_team_id, match_date FROM matches ORDER BY match_date";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            List<Match> matches = new ArrayList<>();
            while (results.next()) {
                matches.add(new Match(
                        results.getLong("id"),
                        results.getLong("home_team_id"),
                        results.getLong("away_team_id"),
                        results.getTimestamp("match_date").toLocalDateTime(),
                        MatchStatus.SCHEDULED
                ));
            }
            return matches;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to list matches", e);
        }
    }
}
