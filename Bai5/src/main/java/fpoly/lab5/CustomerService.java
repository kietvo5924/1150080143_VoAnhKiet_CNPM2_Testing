package fpoly.lab5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class CustomerService {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public void deleteUser(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE id = ?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public String validateAndRegister(String id, String name, String email, String phone,
                                      String address, String password, String confirmPass,
                                      boolean tosAgreed, String gender, String dob) {

        if (!tosAgreed) return "Bạn phải đồng ý với các điều khoản dịch vụ.";

        if (id == null || id.trim().isEmpty()) return "Mã Khách Hàng là bắt buộc.";
        if (id.length() < 6 || id.length() > 10) return "Mã Khách Hàng phải từ 6 đến 10 ký tự.";
        if (!id.matches("[a-zA-Z0-9]+")) return "Mã Khách Hàng chỉ được chứa chữ cái và số.";

        if (name == null || name.trim().isEmpty()) return "Họ và Tên là bắt buộc.";
        if (name.length() < 5 || name.length() > 50) return "Họ và Tên phải từ 5 đến 50 ký tự.";

        if (email == null || email.trim().isEmpty()) return "Email là bắt buộc.";
        if (!Pattern.matches(EMAIL_PATTERN, email)) return "Vui lòng nhập đúng định dạng email (vd: abc@email.com).";

        if (phone == null || phone.trim().isEmpty()) return "Số điện thoại là bắt buộc.";
        if (!phone.matches("\\d+")) return "Số điện thoại chỉ được phép nhập số.";
        if (phone.length() < 10 || phone.length() > 12) return "Số điện thoại phải từ 10 đến 12 số.";

        if (address == null || address.trim().isEmpty()) return "Địa chỉ là bắt buộc.";
        if (address.length() > 255) return "Địa chỉ không được vượt quá 255 ký tự.";

        if (dob != null && !dob.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate birthDate = LocalDate.parse(dob, formatter);
                LocalDate today = LocalDate.now();

                if (Period.between(birthDate, today).getYears() < 5) {
                    return "Bạn phải đủ 18 tuổi để đăng ký.";
                }
            } catch (DateTimeParseException e) {
                return "Ngày sinh không hợp lệ (định dạng MM/dd/yyyy).";
            }
        }

        if (password == null || password.isEmpty()) return "Mật khẩu là bắt buộc.";
        if (password.length() < 8) return "Mật khẩu phải có ít nhất 8 ký tự.";
        if (!password.equals(confirmPass)) return "Mật khẩu xác nhận không trùng khớp.";

        return saveToDatabase(id, name, email, phone, address, password, gender, dob);
    }

    private String saveToDatabase(String id, String name, String email, String phone,
                                  String address, String pass, String gender, String dob) {
        String sql = "INSERT INTO customers (id, fullname, email, phone, address, password, gender, dob) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, pass);
            ps.setString(7, gender);
            ps.setString(8, dob);
            ps.executeUpdate();
            return "Đăng ký tài khoản thành công!";
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique")) {
                if (e.getMessage().contains("customers_pkey")) return "Mã Khách Hàng đã tồn tại, vui lòng chọn mã khác.";
                return "Dữ liệu (ID hoặc Email) đã tồn tại.";
            }
            return "Lỗi Database: " + e.getMessage();
        }
    }
}