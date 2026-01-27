package fpoly.lab5.bai4;

public class HealthCheckUtils {

    public static int calculateFee(int age, String gender) {
        // Kiểm tra ngoại lệ về tuổi (0-145)
        if (age < 0 || age > 145) {
            throw new IllegalArgumentException("Tuổi không hợp lệ (0-145)");
        }

        // 1. Trẻ em (0-17 tuổi): 50 euro (Không phân biệt giới tính)
        if (age <= 17) {
            return 50;
        }

        // 2. Người lớn (Trên 17 tuổi)
        // Chuẩn hóa giới tính để so sánh
        boolean isMale = "Nam".equalsIgnoreCase(gender) || "Male".equalsIgnoreCase(gender);
        boolean isFemale = "Nữ".equalsIgnoreCase(gender) || "Female".equalsIgnoreCase(gender);

        if (isMale) {
            if (age <= 35) return 100; // 18-35 tuổi
            if (age <= 50) return 120; // 36-50 tuổi
            return 140;                // 51-145 tuổi
        } else if (isFemale) {
            if (age <= 35) return 80;  // 18-35 tuổi
            if (age <= 50) return 110; // 36-50 tuổi
            return 140;                // 51-145 tuổi
        } else {
            throw new IllegalArgumentException("Giới tính không hợp lệ");
        }
    }
}