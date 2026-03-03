package dtm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    private WebDriver driver;

    // Các nút bấm chuyển trang
    @FindBy(id = "checkout")
    private WebElement checkoutBtn; // Nút này ở trang Cart

    // Điền thông tin Step 1
    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement zipCode;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    // Lấy thông tin tiền bạc Step 2
    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickNutCheckout() {
        checkoutBtn.click();
    }

    public void nhapThongTinGiaoHang(String fName, String lName, String zip) {
        firstName.sendKeys(fName);
        lastName.sendKeys(lName);
        zipCode.sendKeys(zip);
        continueBtn.click();
    }

    // Cắt bỏ chữ "Item total: $" để lấy đúng số thực (double)
    public double layItemTotal() {
        return Double.parseDouble(itemTotalLabel.getText().replace("Item total: $", ""));
    }

    // Cắt bỏ chữ "Tax: $"
    public double layTax() {
        return Double.parseDouble(taxLabel.getText().replace("Tax: $", ""));
    }

    // Cắt bỏ chữ "Total: $"
    public double layTotal() {
        return Double.parseDouble(totalLabel.getText().replace("Total: $", ""));
    }
}