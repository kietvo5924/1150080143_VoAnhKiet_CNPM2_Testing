package fpoly.lab5.bai4;

public class HealthCheckUtils {

    public static int calculateFee(int age, String gender) {
        if (age < 0 || age > 145) {
            throw new IllegalArgumentException("Tuổi không hợp lệ (0-145)");
        }

        if (age < 17) {
            return 50;
        }

        boolean isMale = "Nam".equalsIgnoreCase(gender) || "Male".equalsIgnoreCase(gender);
        boolean isFemale = "Nữ".equalsIgnoreCase(gender) || "Female".equalsIgnoreCase(gender);

        if (isMale) {
            if (age <= 35) return 100;
            if (age <= 50) return 120;
            return 140;
        } else if (isFemale) {
            if (age <= 35) return 90;
            if (age <= 50) return 110;
            return 140;
        } else {
            throw new IllegalArgumentException("Giới tính không hợp lệ");
        }
    }
}