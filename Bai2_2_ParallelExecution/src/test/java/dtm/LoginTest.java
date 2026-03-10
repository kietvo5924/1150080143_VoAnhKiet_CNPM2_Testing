package dtm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
    }

    @Test(description = "Chạy LoginTest song song")
    public void testLogin() throws InterruptedException {
        System.out.println("LoginTest chạy trên Thread: " + Thread.currentThread().getId());
        DriverFactory.getDriver().get("https://www.saucedemo.com");

        // Dừng 5 giây để bạn kịp chụp màn hình 2 cửa sổ Chrome mở cùng lúc
        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}