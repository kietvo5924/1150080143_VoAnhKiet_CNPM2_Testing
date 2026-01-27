package fpoly.lab5.bai4;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HealthCheckTest {

    // --- KIỂM TRA TRẺ EM (0-17) ---
    @Test
    public void testChild() {
        // Bé trai 10 tuổi -> 50
        assertEquals(50, HealthCheckUtils.calculateFee(10, "Nam"));
        // Bé gái 17 tuổi -> 50
        assertEquals(50, HealthCheckUtils.calculateFee(17, "Nữ"));
    }

    // --- KIỂM TRA NAM ---
    @Test
    public void testMaleAdult() {
        // 18-35 -> 100
        assertEquals(100, HealthCheckUtils.calculateFee(18, "Nam"));
        assertEquals(100, HealthCheckUtils.calculateFee(35, "Nam"));

        // 36-50 -> 120
        assertEquals(120, HealthCheckUtils.calculateFee(36, "Nam"));
        assertEquals(120, HealthCheckUtils.calculateFee(50, "Nam"));

        // 51+ -> 140
        assertEquals(140, HealthCheckUtils.calculateFee(51, "Nam"));
    }

    @Test
    public void testFemaleAdult() {
        // 18-35 -> 80
        assertEquals(80, HealthCheckUtils.calculateFee(18, "Nữ"));
        assertEquals(80, HealthCheckUtils.calculateFee(35, "Nữ"));

        // 36-50 -> 110
        assertEquals(110, HealthCheckUtils.calculateFee(36, "Nữ"));
        assertEquals(110, HealthCheckUtils.calculateFee(50, "Nữ"));

        // 51+ -> 140
        assertEquals(140, HealthCheckUtils.calculateFee(51, "Nữ"));
    }

    // --- KIỂM TRA NGOẠI LỆ ---
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAgeNegative() {
        HealthCheckUtils.calculateFee(-1, "Nam");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGender() {
        HealthCheckUtils.calculateFee(20, "Khac");
    }
}