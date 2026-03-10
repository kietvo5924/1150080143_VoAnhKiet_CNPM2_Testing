package dtm;
import org.testng.annotations.Test;

public class LoginTest {
    @Test(groups = {"smoke", "regression"}, description = "Đăng nhập thành công")
    public void testLoginSuccess() {
        System.out.println("-> LoginTest: Chạy testLoginSuccess (Nhóm: smoke, regression)");
    }

    @Test(groups = {"regression"}, description = "Đăng nhập sai mật khẩu")
    public void testLoginWrongPassword() {
        System.out.println("-> LoginTest: Chạy testLoginWrongPassword (Nhóm: regression)");
    }
}