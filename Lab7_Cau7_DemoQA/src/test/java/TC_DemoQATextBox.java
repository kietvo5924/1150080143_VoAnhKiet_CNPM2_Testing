import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TC_DemoQATextBox {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demoqa.com/text-box");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "TC_QA_01: Nhập email sai định dạng")
    public void testEmailSaiDinhDang() {
        driver.findElement(By.id("userName")).sendKeys("Vo Anh Kiet");
        driver.findElement(By.id("userEmail")).sendKeys("kietvo_invalid");
        driver.findElement(By.id("currentAddress")).sendKeys("Ho Chi Minh");

        WebElement submitBtn = driver.findElement(By.id("submit"));
        // Cuộn xuống
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        try { Thread.sleep(500); } catch (Exception e) {}

        // DÙNG JAVASCRIPT ĐỂ FORCE CLICK XUYÊN QUA QUẢNG CÁO
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        String emailClasses = driver.findElement(By.id("userEmail")).getAttribute("class");
        Assert.assertTrue(emailClasses.contains("field-error"), "Lỗi: Email sai nhưng không bị viền đỏ");
    }

    @Test(description = "TC_QA_02: Nhập hợp lệ toàn bộ form")
    public void testHopLeToanBo() {
        driver.findElement(By.id("userName")).sendKeys("Vo Anh Kiet");
        driver.findElement(By.id("userEmail")).sendKeys("kiet@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Ho Chi Minh");
        driver.findElement(By.id("permanentAddress")).sendKeys("Vietnam");

        WebElement submitBtn = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        try { Thread.sleep(500); } catch (Exception e) {}

        // DÙNG JAVASCRIPT FORCE CLICK
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        Assert.assertTrue(driver.findElement(By.id("name")).getText().contains("Vo Anh Kiet"), "Tên hiển thị sai");
        Assert.assertTrue(driver.findElement(By.id("email")).getText().contains("kiet@gmail.com"), "Email hiển thị sai");
    }
}