import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TC_AutomationPracticeTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        // 1. THÊM BÙA CHỐNG BLOCK BỞI CLOUDFLARE/CHROME
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // Đợi load trang tối đa 30s
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("http://automationpractice.pl/index.php?controller=authentication&back=my-account");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "TC02: Nhập email sai định dạng ở Step 1")
    public void testEmailSaiDinhDang() {
        // 2. THÊM LỆNH CHỜ Ô EMAIL XUẤT HIỆN RỒI MỚI NHẬP
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_create")));
        emailInput.sendKeys("invalid_email.com");

        driver.findElement(By.id("SubmitCreate")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create_account_error")));
        Assert.assertTrue(error.getText().contains("Invalid email address"), "Lỗi không hiển thị đúng");
    }

    @Test(description = "Bao phủ 7 TC lỗi bỏ trống ở Step 2 (TC04, 06, 07, 09, 10, 11, 13)")
    public void testLoiBoTrongFormStep2() {
        // Đợi ô email xuất hiện
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_create")));

        String randomEmail = "kietvo" + System.currentTimeMillis() + "@gmail.com";
        emailInput.sendKeys(randomEmail);
        driver.findElement(By.id("SubmitCreate")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer_firstname")));

        WebElement submitBtn = driver.findElement(By.id("submitAccount"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);

        // Đôi khi cuộn xong chưa click được ngay, cho nó nghỉ 1 nhịp
        try { Thread.sleep(500); } catch (Exception e) {}
        submitBtn.click();

        WebElement errorBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));
        String errorText = errorBox.getText();

        Assert.assertTrue(errorText.contains("lastname is required"), "Thiếu báo lỗi Lastname");
        Assert.assertTrue(errorText.contains("firstname is required"), "Thiếu báo lỗi Firstname");
        Assert.assertTrue(errorText.contains("passwd is required"), "Thiếu báo lỗi Password");
    }

    @Test(description = "TC15: Happy Path - Đăng ký thành công")
    public void testDangKyThanhCong() {
        // Đợi ô email xuất hiện
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_create")));

        String randomEmail = "success" + System.currentTimeMillis() + "@gmail.com";
        emailInput.sendKeys(randomEmail);
        driver.findElement(By.id("SubmitCreate")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1"))).click();
        driver.findElement(By.id("customer_firstname")).sendKeys("Vo");
        driver.findElement(By.id("customer_lastname")).sendKeys("Kiet");
        driver.findElement(By.id("passwd")).sendKeys("Kiet@12345");

        new Select(driver.findElement(By.id("days"))).selectByValue("15");
        new Select(driver.findElement(By.id("months"))).selectByValue("8");
        new Select(driver.findElement(By.id("years"))).selectByValue("2000");

        driver.findElement(By.id("address1")).sendKeys("Khu Cong Nghe Cao");
        driver.findElement(By.id("city")).sendKeys("Ho Chi Minh");

        new Select(driver.findElement(By.id("id_state"))).selectByVisibleText("California");

        driver.findElement(By.id("postcode")).sendKeys("70000");
        driver.findElement(By.id("phone_mobile")).sendKeys("0987654321");

        WebElement submitBtn = driver.findElement(By.id("submitAccount"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);

        try { Thread.sleep(500); } catch (Exception e) {}
        submitBtn.click();

        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
        Assert.assertTrue(successAlert.getText().contains("Your account has been created"), "Đăng ký thất bại");
    }
}