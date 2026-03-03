package dtm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;

    // Các WebElement theo yêu cầu của đề bài
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(xpath = "//button[text()='Add to cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeButtons;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Thêm sản phẩm theo tên */
    public void themSanPhamTheoTen(String tenSanPham) {
        String xpath = "//div[text()='" + tenSanPham + "']/../../..//button[text()='Add to cart']";
        driver.findElement(By.xpath(xpath)).click();
    }

    /** Thêm N sản phẩm đầu tiên trong danh sách */
    public void themNSanPhamDauTien(int n) {
        for (int i = 0; i < n; i++) {
            // SỬA Ở ĐÂY: Luôn click index 0 vì sau khi click, nút đó biến thành Remove
            // và danh sách Add to cart tự động bị thu hẹp lại.
            addToCartButtons.get(0).click();
        }
    }

    /** Xóa tất cả sản phẩm đang có trong giỏ */
    public void xoaTatCaSanPham() {
        int soLuong = removeButtons.size();
        for (int i = 0; i < soLuong; i++) {
            removeButtons.get(0).click(); // Luôn click nút đầu tiên
        }
    }

    /** Trả về số lượng badge giỏ hàng, 0 nếu không có badge */
    public int laySoLuongBadge() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    /** Sort sản phẩm theo tùy chọn: 'az', 'za', 'lohi', 'hilo' */
    public void sortSanPham(String option) {
        Select select = new Select(sortDropdown);
        select.selectByValue(option);
    }

    /** Lấy danh sách tên sản phẩm theo thứ tự hiển thị */
    public List<String> layDanhSachTenSanPham() {
        List<String> names = new ArrayList<>();
        for (WebElement e : itemNames) {
            names.add(e.getText());
        }
        return names;
    }

    /** Lấy danh sách giá sản phẩm theo thứ tự hiển thị */
    public List<Double> layDanhSachGiaSanPham() {
        List<Double> prices = new ArrayList<>();
        for (WebElement e : itemPrices) {
            prices.add(Double.parseDouble(e.getText().replace("$", "")));
        }
        return prices;
    }

    public void clickVaoGioHang() {
        cartLink.click();
    }
}