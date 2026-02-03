package fploy.bai5;

import java.sql.*;

public class JobService {
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS job_title (" +
                "job_title_name VARCHAR(100) PRIMARY KEY, " +
                "job_description VARCHAR(400), " +
                "file_size_kb INT, " +
                "note VARCHAR(400))";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean addJob(String title, String desc, int fileSize, String note) throws SQLException {
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Title empty");
        if (title.length() > 100) throw new IllegalArgumentException("Title too long");
        if (desc != null && desc.length() > 400) throw new IllegalArgumentException("Desc too long");
        if (fileSize > 1024) throw new IllegalArgumentException("File too large");

        String sql = "INSERT INTO job_title VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, desc);
            pstmt.setInt(3, fileSize);
            pstmt.setString(4, note);
            return pstmt.executeUpdate() > 0;
        }
    }
}