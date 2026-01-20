import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;

public class TestBai2 {

    // Test case 1: Tính toán đúng
    // Đa thức: 1 + 2x (n=1, hệ số là 1 và 2)
    // Với x = 2.0 -> Kết quả = 1 + 2*(2^1) = 5
    @Test
    public void testCalValid() {
        int n = 1;
        List<Integer> heSo = Arrays.asList(1, 2);
        double x = 2.0;

        Bai2 bai2 = new Bai2(n, heSo);
        int actual = bai2.Cal(x);

        Assert.assertEquals(5, actual);
    }

    // Test case 2: Bậc n = 2 (cần 3 hệ số) nhưng chỉ nhập 2 hệ số -> Phải báo lỗi
    @Test(expected = IllegalArgumentException.class)
    public void testMissingCoefficients() {
        int n = 2;
        List<Integer> heSo = Arrays.asList(1, 2); // Thiếu 1 số

        // Dòng này sẽ ném ra ngoại lệ, JUnit sẽ bắt và coi là Pass
        new Bai2(n, heSo);
    }

    // Test case 3: Bậc n bị âm -> Phải báo lỗi
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDegree() {
        int n = -1;
        List<Integer> heSo = Arrays.asList(1);

        new Bai2(n, heSo);
    }
}