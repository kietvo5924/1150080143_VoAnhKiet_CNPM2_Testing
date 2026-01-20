import org.junit.Test;
import org.junit.Assert;

public class TestBai1 {

    // Test case 1: Kiểm tra khi n = 0 (Kỳ vọng trả về 1.0)
    @Test
    public void testPowerZero() {
        double x = 10.0;
        int n = 0;
        double expected = 1.0;
        double actual = Bai1.power(x, n);

        Assert.assertEquals(expected, actual, 0.0001);
    }

    // Test case 2: Kiểm tra khi n > 0 (Ví dụ: 2^3 = 8)
    @Test
    public void testPowerPositive() {
        double x = 2.0;
        int n = 3;
        double expected = 8.0; // 2^3 = 8
        double actual = Bai1.power(x, n);

        Assert.assertEquals(expected, actual, 0.0001);
    }

    // Test case 3: Kiểm tra khi n < 0 (Ví dụ: 2^-2 = 0.25)
    @Test
    public void testPowerNegative() {
        double x = 2.0;
        int n = -2;
        double expected = 0.25; // 1/(2^2) = 0.25
        double actual = Bai1.power(x, n);

        Assert.assertEquals(expected, actual, 0.0001);
    }
}