package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.TextBoxPage;

import java.time.Duration;

public class TextBoxWhiteBoxTest {
    private WebDriver driver;
    private TextBoxPage textBoxPage;

    @BeforeMethod
    public void setUp() {
        // Setup Chrome ẩn danh để tránh cache
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://demoqa.com/text-box");
        textBoxPage = new TextBoxPage(driver); // Khởi tạo Page Object
    }

    @Test(description = "TC_GUI_01: Email hợp lệ -> Đi vào nhánh True, hiển thị Output")
    public void testValidEmail() {
        textBoxPage.enterFullName("Vo Anh Kiet");
        textBoxPage.enterEmail("nguyenvana@gmail.com");
        textBoxPage.enterCurrentAddress("Ho Chi Minh City");

        textBoxPage.clickSubmit();

        // Kiểm tra: Output hiển thị, không có viền đỏ
        Assert.assertTrue(textBoxPage.isOutputDisplayed(), "Lỗi: Form không hiển thị output!");
        Assert.assertFalse(textBoxPage.isEmailErrorDisplayed(), "Lỗi: Ô email bị viền đỏ sai logic!");
    }

    @Test(description = "TC_GUI_02: Để trống form -> Đi vào nhánh True, không hiển thị Output do rỗng")
    public void testEmptyForm() {
        textBoxPage.clickSubmit();

        // Kiểm tra: Không báo lỗi viền đỏ, Output rỗng
        Assert.assertFalse(textBoxPage.isOutputDisplayed(), "Lỗi: Output vẫn hiển thị text dù form rỗng!");
        Assert.assertFalse(textBoxPage.isEmailErrorDisplayed(), "Lỗi: Ô email rỗng không được báo lỗi!");
    }

    @DataProvider(name = "invalidEmails")
    public Object[][] getInvalidEmails() {
        return new Object[][] {
                {"Khuyết @", "nguyenvanagmail.com"},
                {"Khuyết Domain", "nguyenvana@"},
                {"Chứa khoảng trắng", "nguyen vana@gmail.com"}
        };
    }

    @Test(dataProvider = "invalidEmails", description = "TC_GUI_05 -> 08: Email sai định dạng -> Đi vào nhánh False, báo viền đỏ")
    public void testInvalidEmail(String testName, String invalidEmail) {
        textBoxPage.enterFullName("Test Error");
        textBoxPage.enterEmail(invalidEmail);

        textBoxPage.clickSubmit();

        // Kiểm tra: Phải có viền đỏ báo lỗi, Output KHÔNG được hiển thị
        Assert.assertTrue(textBoxPage.isEmailErrorDisplayed(), "Lỗi tại TC '" + testName + "': Không hiện viền đỏ cảnh báo!");
        Assert.assertFalse(textBoxPage.isOutputDisplayed(), "Lỗi tại TC '" + testName + "': Form vẫn render output dù email sai!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Đóng trình duyệt sau mỗi test case
        }
    }
}