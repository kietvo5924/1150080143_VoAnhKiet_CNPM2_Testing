import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiUiIntegrationTest {
    private WebDriver driver;
    private String apiToken;
    private boolean isApiAlive = false;

    @BeforeClass
    public void setupBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @BeforeMethod
    public void apiPreconditionLogin() {
        System.out.println("--- Buoc API check: Goi POST login de lay token ---");

        String loginBody = "{\"username\" : \"admin\", \"password\" : \"password123\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginBody)
                .when()
                .post("https://restful-booker.herokuapp.com/auth");

        if (response.getStatusCode() == 200) {
            apiToken = response.jsonPath().getString("token");
            System.out.println("Lay token thanh cong: " + apiToken);
        } else {
            apiToken = null;
            System.out.println("Lay token that bai");
        }
    }

    @Test(description = "Phan A: UI Verification sau khi API Login Pass")
    public void testUiLoginVerification() {
        // Kiem tra dieu kien tien quyet tu API
        if (apiToken == null) {
            throw new SkipException("API Login fail nen SKIP test UI nay");
        }

        // --- Buoc UI action: Dang nhap vao SauceDemo ---
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // --- Buoc Assertion: Xac minh URL va Title ---
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "URL khong chua inventory");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title trang khong khop");
    }

    // PHẦN B: LUỒNG TÍCH HỢP ĐẦY ĐỦ
    @Test(description = "Phan B: Full Integration Flow - Cart Verification")
    public void testFullIntegrationFlow() {
        // --- Buoc API check: Kiem tra server dang song ---
        Response response = given().get("https://jsonplaceholder.typicode.com/users");
        isApiAlive = (response.getStatusCode() == 200);

        if (!isApiAlive) {
            throw new SkipException("Server API dang bao tri nen SKIP test UI nay");
        }

        // --- Buoc UI action: Thuc hien mua hang tren UI ---
        driver.get("https://www.saucedemo.com/");
        // Dang nhap
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Them 2 san pham
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        // --- Buoc Assertion: Kiem tra badge gio hang ---
        String badgeCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badgeCount, "2", "So luong san pham trong gio hang sai");

        // Vao gio hang xac nhan
        driver.findElement(By.className("shopping_cart_link")).click();
        int itemsInCart = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemsInCart, 2, "Thuc te trong gio hang khong co 2 san pham");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}