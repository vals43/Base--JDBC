package PROG3.DB;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String JDBC_URL = dotenv.get("JDBC_URL");
    private static final String USERNAME = dotenv.get("USERNAME");
    private static final String PASSWORD = dotenv.get("PASSWORD");

    public Connection getDBConnection() throws SQLException {

        if (JDBC_URL == null || USERNAME == null || PASSWORD == null) {
            throw new SQLException("Variables JDBC manquantes dans le fichier .env");
        }

        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public void closeDBConnection() throws SQLException {
        getDBConnection().close();
    }
}
