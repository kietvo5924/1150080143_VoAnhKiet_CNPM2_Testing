package fpoly.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Annotation này báo cho JUnit biết đây là một Suite
@RunWith(Suite.class)

// Khai báo danh sách các class test muốn chạy cùng nhau
@Suite.SuiteClasses({
        SuiteTest1.class,
        SuiteTest2.class
})
public class JunitTest {

}