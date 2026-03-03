package dtm.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public abstract class BaseTest {
    // Sử dụng ThreadLocal để hỗ trợ chạy test song song (parallel execution)
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeMethod
    public void setUp(Method method) {
        // Ghi log tên test đang chạy
        System.out.println("[START] Đang chạy test: " + method.getName());

        // Khởi tạo ChromeDriver qua WebDriverManager (không cần tải file chromedriver.exe thủ công)
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Maximize window, set implicit wait 10s
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Lưu driver vào luồng hiện tại
        driverThreadLocal.set(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        WebDriver driver = getDriver();
        if (driver != null) {
            // Nếu result là FAILURE, chụp screenshot lưu vào /screenshots/
            if (ITestResult.FAILURE == result.getStatus()) {
                try {
                    TakesScreenshot ts = (TakesScreenshot) driver;
                    File source = ts.getScreenshotAs(OutputType.FILE);
                    File destFolder = new File("screenshots");
                    if (!destFolder.exists()) {
                        destFolder.mkdirs(); // Tự động tạo thư mục nếu chưa có
                    }
                    File dest = new File(destFolder, result.getName() + ".png");
                    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("[FAIL] Đã chụp màn hình lỗi và lưu tại: " + dest.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Lỗi khi chụp màn hình: " + e.getMessage());
                }
            }

            // Đóng trình duyệt và dọn dẹp biến ThreadLocal
            driver.quit();
            driverThreadLocal.remove();
            System.out.println("[END] Đã đóng trình duyệt cho test: " + result.getName());
            System.out.println("--------------------------------------------------");
        }
    }

    public WebDriver getDriver() {
        // Trả về driver của thread hiện tại
        return driverThreadLocal.get();
    }
}