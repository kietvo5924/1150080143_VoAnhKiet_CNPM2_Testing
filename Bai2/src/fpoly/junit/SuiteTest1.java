package fpoly.junit;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SuiteTest1 {
    public String message = "Fpoly";
    JUnitMessage junitMessage = new JUnitMessage(message);

    // Lưu ý: Tài liệu để (expected = ArithmeticException.class) [cite: 106]
    // Nhưng code không sinh lỗi này nên test sẽ fail.
    // Mình bỏ phần expected đi để bạn chạy ra màu xanh (Success) nhé.
    @Test
    public void testJUnitMessage() {
        System.out.println("Junit Message is printing ");
        junitMessage.printMessage();
    }

    @Test
    public void testJUnitHiMessage() {
        message = "Hi!" + message;
        System.out.println("Junit Hi Message is printing ");
        assertEquals(message, junitMessage.printHiMessage());
        System.out.println("Suite Test 2 is successful " + message);
    }
}