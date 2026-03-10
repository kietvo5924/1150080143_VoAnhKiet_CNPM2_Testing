package dtm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    // 1. Mỗi @Test dùng @BeforeMethod để mở trình duyệt mới
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // 2. Cài đặt Explicit Wait (WebDriverWait)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
    }

    @Test(description = "Nhập user/pass hợp lệ -> kiểm tra chuyển sang /inventory.html")
    public void testLoginSuccess() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Chờ URL chứa 'inventory.html' thay vì dùng Thread.sleep()
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        String currentUrl = driver.getCurrentUrl();
        // 3. Assert có thông báo lỗi rõ ràng
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Đăng nhập thành công nhưng không chuyển hướng đến trang inventory!");
    }

    @Test(description = "Nhập sai mật khẩu -> kiểm tra thông báo lỗi xuất hiện")
    public void testLoginWrongPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        Assert.assertTrue(errorElement.isDisplayed(), "Thông báo lỗi không xuất hiện khi nhập sai mật khẩu!");
    }

    @Test(description = "Bỏ trống username -> kiểm tra thông báo 'Username is required'")
    public void testLoginEmptyUsername() {
        // Cố tình chỉ nhập password, bỏ trống username
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String actualError = errorElement.getText();

        Assert.assertTrue(actualError.contains("Username is required"), "Sai thông báo lỗi khi bỏ trống Username! Thực tế: " + actualError);
    }

    @Test(description = "Bỏ trống password -> kiểm tra thông báo 'Password is required'")
    public void testLoginEmptyPassword() {
        // Cố tình chỉ nhập username, bỏ trống password
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();

        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String actualError = errorElement.getText();

        Assert.assertTrue(actualError.contains("Password is required"), "Sai thông báo lỗi khi bỏ trống Password! Thực tế: " + actualError);
    }

    @Test(description = "Dùng 'locked_out_user' -> kiểm tra thông báo 'Sorry, this user has been locked out'")
    public void testLoginLockedUser() {
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String actualError = errorElement.getText();

        Assert.assertTrue(actualError.contains("Sorry, this user has been locked out"), "Sai thông báo lỗi cho user bị khóa! Thực tế: " + actualError);
    }

    // 1. Dùng @AfterMethod để đóng trình duyệt
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}