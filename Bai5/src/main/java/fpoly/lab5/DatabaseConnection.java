package fpoly.lab5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // LƯU Ý: Đã đổi cổng thành 5433 theo yêu cầu của bạn
    private static final String URL = "jdbc:postgresql://localhost:5433/lab5_db";
    private static final String USER = "admin";
    private static final String PASS = "password123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Hàm này chạy 1 lần khi mở app để tạo bảng nếu chưa có
    public static void initDB() {
        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
                "id VARCHAR(10) PRIMARY KEY, " +
                "fullname VARCHAR(50), " +
                "email VARCHAR(100), " +
                "phone VARCHAR(12), " +
                "address VARCHAR(255), " +
                "password VARCHAR(255), " +
                "gender VARCHAR(10), " +
                "dob VARCHAR(20)" +
                ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Kết nối Database cổng 5433 thành công & Đã tạo bảng!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi kết nối! Kiểm tra lại Docker xem đã chạy chưa.");
        }
    }
}