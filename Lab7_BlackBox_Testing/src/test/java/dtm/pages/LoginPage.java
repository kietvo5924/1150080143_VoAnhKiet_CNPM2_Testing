package dtm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    // Khai báo WebElement bằng @FindBy
    @FindBy(id = "user-name")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "div[data-test='error']")
    private WebElement errorMessage; // Thông báo lỗi xuất hiện phía trên form

    // Constructor khởi tạo Page Object
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void nhapUsername(String username) {
        userNameField.clear();
        // Xử lý an toàn nếu truyền null từ DataProvider
        if (username != null) {
            userNameField.sendKeys(username);
        }
    }

    public void nhapPassword(String password) {
        passwordField.clear();
        if (password != null) {
            passwordField.sendKeys(password);
        }
    }

    public void clickDangNhap() {
        loginButton.click();
    }

    /** Thực hiện đăng nhập đầy đủ */
    public void dangNhap(String user, String pass) {
        nhapUsername(user);
        nhapPassword(pass);
        clickDangNhap();
    }

    /** Trả về nội dung thông báo lỗi, null nếu không có lỗi */
    public String layThongBaoLoi() {
        try {
            if (errorMessage.isDisplayed()) {
                return errorMessage.getText();
            }
        } catch (Exception e) {
            // Không tìm thấy element lỗi nghĩa là không có lỗi
            return null;
        }
        return null;
    }

    /** Kiểm tra đã chuyển sang trang inventory chưa */
    public boolean isDangTrangSanPham() {
        // Nếu URL chứa inventory.html tức là đã đăng nhập thành công
        return driver.getCurrentUrl().contains("/inventory.html");
    }
}