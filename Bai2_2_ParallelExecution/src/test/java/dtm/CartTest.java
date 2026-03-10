package dtm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver("chrome");
    }

    @Test(description = "Chạy CartTest song song")
    public void testCart() throws InterruptedException {
        System.out.println("CartTest chạy trên Thread: " + Thread.currentThread().getId());
        DriverFactory.getDriver().get("https://www.saucedemo.com/inventory.html");

        // Dừng 5 giây để bạn kịp chụp màn hình
        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}