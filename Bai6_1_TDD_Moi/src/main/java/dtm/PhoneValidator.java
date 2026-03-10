package dtm;

public class PhoneValidator {
    public static boolean isValid(String phone) {
        // D1: Kiểm tra Null hoặc rỗng
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        // D2: Kiểm tra ký tự hợp lệ (chỉ cho phép số, dấu cộng, khoảng trắng)
        if (!phone.matches("[0-9+ ]+")) {
            return false;
        }

        // Chuẩn hóa: Bỏ khoảng trắng
        String normalized = phone.replace(" ", "");

        // D3: Đổi +84 thành 0
        if (normalized.startsWith("+84")) {
            normalized = "0" + normalized.substring(3);
        }

        // D4: Độ dài phải đúng 10 số
        if (normalized.length() != 10) {
            return false;
        }

        // D5: Kiểm tra đầu số mạng hợp lệ (03, 05, 07, 08, 09)
        if (!normalized.matches("0[35789]\\d{8}")) {
            return false;
        }

        return true;
    }
}