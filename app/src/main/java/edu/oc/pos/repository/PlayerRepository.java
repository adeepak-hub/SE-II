package edu.oc.pos.repository;

import edu.oc.pos.db.Database;
import edu.oc.pos.domain.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    private final Database database;

    public PlayerRepository(Database database) {
        this.database = database;
    }

    public Player save(Player player) {
        String sql = "INSERT INTO players (team_id, name, jersey_number) VALUES (?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, player.getTeamId());
            statement.setString(2, player.getName());
            if (player.getJerseyNumber() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, player.getJerseyNumber());
            }
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    player.setId(keys.getLong(1));
                }
            }
            return player;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save player", e);
        }
    }

    public List<Player> findByTeamId(long teamId) {
        String sql = "SELECT id, team_id, name, jersey_number FROM players WHERE team_id = ? ORDER BY jersey_number";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, teamId);
            try (ResultSet results = statement.executeQuery()) {
                List<Player> players = new ArrayList<>();
                while (results.next()) {
                    Integer jersey = results.getObject("jersey_number") == null ? null : results.getInt("jersey_number");
                    players.add(new Player(
                            results.getLong("id"),
                            results.getLong("team_id"),
                            results.getString("name"),
                            jersey
                    ));
                }
                return players;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to list players", e);
        }
    }
}
