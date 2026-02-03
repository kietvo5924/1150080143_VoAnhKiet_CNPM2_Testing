package fpoly.bai6;

import java.sql.*;

public class UserService {
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(50) PRIMARY KEY, " +
                "password VARCHAR(50) NOT NULL, " +
                "fullname VARCHAR(100), " +
                "email VARCHAR(100))";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean updateUser(String user, String pass, String name, String email) throws SQLException {
        if (user == null || user.isEmpty()) throw new IllegalArgumentException("Username is required");
        if (pass == null || pass.length() < 3) throw new IllegalArgumentException("Password too short");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Invalid email");

        String sql = "INSERT INTO users (username, password, fullname, email) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (username) DO UPDATE SET password=?, fullname=?, email=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user); pstmt.setString(2, pass);
            pstmt.setString(3, name); pstmt.setString(4, email);
            pstmt.setString(5, pass); pstmt.setString(6, name);
            pstmt.setString(7, email);
            return pstmt.executeUpdate() > 0;
        }
    }
}