package fpoly.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        // Dòng này ra lệnh chạy class JunitAnnotationsExample
        Result result = JUnitCore.runClasses(JunitAnnotationsExample.class);

        // Vòng lặp này in ra lỗi nếu có
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        // In kết quả cuối cùng: true (thành công) hoặc false (thất bại)
        System.out.println("Result==" + result.wasSuccessful());
    }
}