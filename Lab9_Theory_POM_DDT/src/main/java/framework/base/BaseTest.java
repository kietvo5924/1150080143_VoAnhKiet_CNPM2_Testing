package framework.base;

import framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.time.Duration;

public abstract class BaseTest {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    protected WebDriver getDriver() { return tlDriver.get(); }

    @Parameters({"browser", "env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) throws Exception {
        System.setProperty("env", env);
        String gridUrl = System.getProperty("grid.url"); // Nhận link từ lệnh mvn
        WebDriver driver;

        if (gridUrl != null && !gridUrl.isEmpty()) {
            // --- CẤU HÌNH GỬI LỆNH SANG DOCKER (GRID) ---
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName(browser);
            driver = new RemoteWebDriver(new URL(gridUrl + "/wd/hub"), caps);
        } else {
            // --- CẤU HÌNH CHẠY LOCAL NHƯ CŨ ---
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            driver = new org.openqa.selenium.chrome.ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(framework.config.ConfigReader.getInstance().getBaseUrl());
        tlDriver.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            framework.utils.ScreenshotUtil.capture(getDriver(), result.getName());
        }
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }
}