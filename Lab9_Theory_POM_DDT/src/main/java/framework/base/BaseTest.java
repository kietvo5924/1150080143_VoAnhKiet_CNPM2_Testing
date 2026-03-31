package framework.base;

import framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.net.URL;
import java.time.Duration;

public abstract class BaseTest {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    protected WebDriver getDriver() { return tlDriver.get(); }

    @Parameters({"browser", "env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) throws Exception {
        System.setProperty("env", env);
        String gridUrl = System.getProperty("grid.url");
        WebDriver driver;

        if (gridUrl != null && !gridUrl.isEmpty()) {
            // --- KẾT NỐI VỚI SELENIUM GRID TRÊN DOCKER ---
            org.openqa.selenium.remote.DesiredCapabilities caps = new org.openqa.selenium.remote.DesiredCapabilities();
            caps.setBrowserName(browser);
            // Grid 4 đôi khi không cần /wd/hub, nếu lỗi hãy thử bỏ đoạn /wd/hub đi
            driver = new RemoteWebDriver(new URL(gridUrl + "/wd/hub"), caps);
        } else {
            // --- CHẠY LOCAL ---
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigReader.getInstance().getBaseUrl());
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