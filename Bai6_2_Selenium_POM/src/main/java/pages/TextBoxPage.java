package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextBoxPage {
    private WebDriver driver;

    // 1. Định nghĩa Locators (Các phần tử trên trang)
    private By txtFullName = By.id("userName");
    private By txtEmail = By.id("userEmail");
    private By txtCurrentAddress = By.id("currentAddress");
    private By txtPermanentAddress = By.id("permanentAddress");
    private By btnSubmit = By.id("submit");
    private By divOutput = By.id("output");

    // Locator để kiểm tra ô email có bị viền đỏ không (thuộc tính class có chứa field-error)
    private By emailErrorClass = By.cssSelector("input#userEmail.field-error");

    // 2. Constructor
    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
    }

    // 3. Các phương thức thao tác (Actions)
    public void enterFullName(String name) {
        driver.findElement(txtFullName).clear();
        driver.findElement(txtFullName).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(txtEmail).clear();
        driver.findElement(txtEmail).sendKeys(email);
    }

    public void enterCurrentAddress(String address) {
        driver.findElement(txtCurrentAddress).clear();
        driver.findElement(txtCurrentAddress).sendKeys(address);
    }

    public void clickSubmit() {
        // Form này thi thoảng bị quảng cáo che nút submit, dùng JS Click để đảm bảo luôn bấm được
        WebElement submitButton = driver.findElement(btnSubmit);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }

    // 4. Các phương thức kiểm tra kết quả (Verifications)
    public boolean isOutputDisplayed() {
        // Kiểm tra khối output có chứa text bên trong hay không
        WebElement output = driver.findElement(divOutput);
        return output.getText().length() > 0;
    }

    public boolean isEmailErrorDisplayed() {
        // Nếu tìm thấy phần tử mang class field-error thì trả về true
        return !driver.findElements(emailErrorClass).isEmpty();
    }
}