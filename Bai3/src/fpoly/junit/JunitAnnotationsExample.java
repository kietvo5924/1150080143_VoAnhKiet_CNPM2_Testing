package fpoly.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JunitAnnotationsExample {
    private ArrayList<String> list;

    // @BeforeClass: Chạy 1 lần duy nhất lúc bắt đầu, trước tất cả các test
    @BeforeClass
    public static void m1() {
        System.out.println("Using @BeforeClass, executed before all test cases");
    }

    // @Before: Chạy lặp lại trước MỖI test case (dùng để reset dữ liệu)
    @Before
    public void m2() {
        list = new ArrayList<String>();
        System.out.println("Using @Before annotations, executed before each test cases");
    }

    // @AfterClass: Chạy 1 lần duy nhất lúc kết thúc, sau khi chạy xong hết các test
    @AfterClass
    public static void m3() {
        System.out.println("Using @AfterClass, executed after all test cases");
    }

    // @After: Chạy lặp lại sau MỖI test case
    @After
    public void m4() {
        list.clear();
        System.out.println("Using @After, executed after each test cases");
    }

    // @Test: Một test case bình thường
    @Test
    public void m5() {
        list.add("test");
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    // @Ignore: Test case này bị bỏ qua, không chạy
    @Ignore
    public void m6() {
        System.out.println("Using @Ignore, this execution is ignored");
    }

    // @Test(timeout): Test case sẽ lỗi nếu chạy lâu quá 10 mili-giây
    @Test(timeout = 10)
    public void m7() {
        System.out.println("Using @Test(timeout), enforce timeout in JUnit4");
    }

    // @Test(expected): Test case này KỲ VỌNG sẽ xảy ra lỗi NoSuchMethodException
    // Nếu code ném ra lỗi này -> Test PASS (Xanh)
    // Nếu code chạy ngon lành -> Test FAIL (Đỏ)
    @Test(expected = NoSuchMethodException.class)
    public void m8() throws NoSuchMethodException {
        System.out.println("Using @Test(expected), check for specified exception");

        // Quan trọng: Phải ném lỗi ra thì test mới Xanh được
        throw new NoSuchMethodException();
    }
}