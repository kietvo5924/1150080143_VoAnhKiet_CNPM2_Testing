package dtm.tests;

import dtm.base.BaseTest;
import dtm.data.DangNhapData;
import dtm.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_DangNhapTest extends BaseTest {

    @Test(
            dataProvider = "du_lieu_dang_nhap",
            dataProviderClass = DangNhapData.class,
            description = "Kiểm thử đăng nhập với nhiều bộ dữ liệu"
    )
    public void kiemThuDangNhap(String username, String password, String ketQuaMongDoi, String moTa) {
        WebDriver driver = getDriver();
        // 1. Mở trang web
        driver.get("https://www.saucedemo.com");

        // 2. Khởi tạo LoginPage
        LoginPage loginPage = new LoginPage(driver);

        // 3. Thực hiện đăng nhập
        loginPage.dangNhap(username, password);

        // 4. Kiểm tra kết quả dựa vào ketQuaMongDoi bằng switch-case
        switch (ketQuaMongDoi) {
            case "THÀNH CÔNG":
                Assert.assertTrue(loginPage.isDangTrangSanPham(),
                        "FAIL: " + moTa + " - Lẽ ra phải chuyển sang trang Sản phẩm.");
                break;

            case "BỊ KHÓA":
                String loiKhoa = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiKhoa, "FAIL: " + moTa + " - Không thấy thông báo lỗi.");
                Assert.assertTrue(loiKhoa.contains("locked out"),
                        "FAIL: " + moTa + " - Thông báo lỗi không chứa chữ 'locked out'.");
                break;

            case "SAI THÔNG TIN":
                String loiSaiThongTin = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiSaiThongTin, "FAIL: " + moTa + " - Không thấy thông báo lỗi.");
                Assert.assertTrue(loiSaiThongTin.contains("Username and password do not match"),
                        "FAIL: " + moTa + " - Báo lỗi sai nội dung.");
                break;

            case "TRỐNG USERNAME":
                String loiUser = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiUser, "FAIL: " + moTa + " - Không thấy thông báo lỗi.");
                Assert.assertTrue(loiUser.contains("Username is required"),
                        "FAIL: " + moTa + " - Phải báo lỗi yêu cầu nhập Username.");
                break;

            case "TRỐNG PASSWORD":
                String loiPass = loginPage.layThongBaoLoi();
                Assert.assertNotNull(loiPass, "FAIL: " + moTa + " - Không thấy thông báo lỗi.");
                Assert.assertTrue(loiPass.contains("Password is required"),
                        "FAIL: " + moTa + " - Phải báo lỗi yêu cầu nhập Password.");
                break;

            default:
                Assert.fail("Kết quả mong đợi không hợp lệ trong DataProvider: " + ketQuaMongDoi);
        }
    }
}