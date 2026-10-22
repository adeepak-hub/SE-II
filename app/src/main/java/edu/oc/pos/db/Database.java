package edu.oc.pos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String url;

    public Database(String url) {
        this.url = url;
        initializeSchema();
    }

    public static Database inMemory(String name) {
        return new Database("jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1;MODE=REGULAR");
    }

    public static Database fileBacked() {
        return new Database("jdbc:h2:./soccer-league;AUTO_SERVER=TRUE");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void initializeSchema() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS teams (
                        id IDENTITY PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        home_venue VARCHAR(255),
                        coach_name VARCHAR(255)
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS players (
                        id IDENTITY PRIMARY KEY,
                        team_id BIGINT NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        jersey_number INT,
                        FOREIGN KEY (team_id) REFERENCES teams(id)
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS matches (
                        id IDENTITY PRIMARY KEY,
                        home_team_id BIGINT NOT NULL,
                        away_team_id BIGINT NOT NULL,
                        match_date TIMESTAMP NOT NULL,
                        FOREIGN KEY (home_team_id) REFERENCES teams(id),
                        FOREIGN KEY (away_team_id) REFERENCES teams(id)
                    )
                    """);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize database schema", e);
        }
    }
}
