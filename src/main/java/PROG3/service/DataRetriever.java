package PROG3.service;

import PROG3.DB.DBConnection;
import PROG3.model.ContinentEnum;
import PROG3.model.Player;
import PROG3.model.PlayerPositionEnum;
import PROG3.model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection dbConnection = new DBConnection();

    public Team findTeamById(Integer id) throws SQLException {

        Team team = null;

        String teamQuery = """
                SELECT id, name , continent
                FROM Team
                WHERE id = ?
                """;

        String playerQuery = """
                SELECT id, name, age, position
                FROM Player
                WHERE id_team = ?
                """;

        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement teamStmt = connection.prepareStatement(teamQuery)) {

            teamStmt.setInt(1, id);
            ResultSet teamRs = teamStmt.executeQuery();

            if (teamRs.next()) {
                team = new Team(
                        teamRs.getInt("id"),
                        teamRs.getString("name"),
                        ContinentEnum.valueOf(teamRs.getString("continent"))
                );

                try (PreparedStatement playerStmt = connection.prepareStatement(playerQuery)) {
                    playerStmt.setInt(1, id);
                    ResultSet playerRs = playerStmt.executeQuery();

                    while (playerRs.next()) {
                        Player player = new Player(
                                playerRs.getInt("id"),
                                playerRs.getString("name"),
                                playerRs.getInt("age"),
                                PlayerPositionEnum.valueOf(playerRs.getString("position")),
                                team
                        );
                        team.getPlayers().add(player);
                    }
                }
            }
        }

        return team;
    }


    public List<Player> findPlayers(int page, int size) throws SQLException {

        List<Player> players = new ArrayList<>();

        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("page et size doivent être > 0");
        }

        String query = """
            SELECT p.id, p.name, p.age, p.position,
                   t.id AS team_id, t.name AS team_name, t.continent
            FROM Player p
            LEFT JOIN Team t ON p.id_team = t.id
            ORDER BY p.id
            LIMIT ? OFFSET ?
            """;

        int offset = (page - 1) * size;

        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Team team = null;
                if (rs.getInt("team_id") != 0) {
                    team = new Team(
                            rs.getInt("team_id"),
                            rs.getString("team_name"),
                            ContinentEnum.valueOf(rs.getString("continent"))
                    );
                }

                Player player = new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        PlayerPositionEnum.valueOf(rs.getString("position")),
                        team
                );

                players.add(player);
            }
        }

        return players;
    }


}
