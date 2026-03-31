package tests;

import framework.base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Web Testing")
@Feature("Login Feature")
public class AllureReportTest extends BaseTest {

    @Test(description = "Kiểm tra đăng nhập thất bại và chụp ảnh màn hình")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Mô tả: Test case này sẽ cố tình fail để kiểm tra tính năng đính kèm ảnh của Allure.")
    public void testLoginFailureCapture() {
        navigateToUrl("https://www.saucedemo.com");
        performLogin("standard_user", "wrong_password");
        verifyErrorMessage();
    }

    @Step("Bước 1: Truy cập trang web {0}")
    public void navigateToUrl(String url) {
        driver.get(url);
    }

    @Step("Bước 2: Nhập username và password")
    public void performLogin(String user, String pass) {
        driver.findElement(By.id("user-name")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("login-button")).click();
    }

    @Step("Bước 3: Xác minh thông báo lỗi")
    public void verifyErrorMessage() {
        // Cố tình tạo lỗi so sánh để Allure chụp ảnh màn hình
        String error = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertEquals(error, "Expected Error Message", "Thông báo lỗi không đúng!");
    }
}