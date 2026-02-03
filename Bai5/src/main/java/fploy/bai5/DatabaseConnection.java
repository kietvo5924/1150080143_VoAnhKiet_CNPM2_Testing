package fploy.bai5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String password = "123";
        return DriverManager.getConnection(url, user, password);
    }
}