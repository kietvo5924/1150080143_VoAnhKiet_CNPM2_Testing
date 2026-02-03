package fploy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrganizationService {

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS organization_unit (" +
                "unit_id VARCHAR(50) PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "description TEXT)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean addUnit(String id, String name, String desc) throws SQLException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        String sql = "INSERT INTO organization_unit (unit_id, name, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, desc);
            return pstmt.executeUpdate() > 0;
        }
    }
}