package dtm.tests;

import dtm.base.BaseTest;
import dtm.pages.CheckoutPage;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC_GioHangTest extends BaseTest {
    InventoryPage inventoryPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void chuanBi() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com");

        // Đăng nhập trước mỗi test
        LoginPage loginPage = new LoginPage(driver);
        loginPage.dangNhap("standard_user", "secret_sauce");

        // Khởi tạo các Page Object
        inventoryPage = new InventoryPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test(groups={"smoke"}, description="TC_CART_001: Thêm 1 sản phẩm -> badge = 1")
    public void themMotSanPham() {
        inventoryPage.themNSanPhamDauTien(1);
        Assert.assertEquals(inventoryPage.laySoLuongBadge(), 1, "Badge giỏ hàng phải bằng 1");
    }

    @Test(groups={"smoke"}, description="TC_CART_002: Thêm 3 sản phẩm -> badge = 3")
    public void them3SanPham() {
        inventoryPage.themNSanPhamDauTien(3);
        Assert.assertEquals(inventoryPage.laySoLuongBadge(), 3, "Badge giỏ hàng phải bằng 3");
    }

    @Test(groups={"regression"}, description="TC_CART_003: Xoá hết -> giỏ trống")
    public void xoaHetSanPham() {
        inventoryPage.themNSanPhamDauTien(2); // Thêm thử 2 cái
        inventoryPage.xoaTatCaSanPham(); // Rồi xóa hết
        Assert.assertEquals(inventoryPage.laySoLuongBadge(), 0, "Giỏ hàng phải trống (badge = 0)");
    }

    @Test(groups={"regression"}, description="TC_CART_004: Sort giá tăng dần - đúng thứ tự")
    public void sortGiaTangDan() {
        // Sort từ thấp đến cao
        inventoryPage.sortSanPham("lohi");

        List<Double> danhSachGia = inventoryPage.layDanhSachGiaSanPham();

        // Kiểm tra xem giá sau có lớn hơn hoặc bằng giá trước không
        boolean isSorted = true;
        for (int i = 0; i < danhSachGia.size() - 1; i++) {
            if (danhSachGia.get(i) > danhSachGia.get(i + 1)) {
                isSorted = false;
                break;
            }
        }
        Assert.assertTrue(isSorted, "Danh sách sản phẩm không được xếp theo giá tăng dần");
    }

    @Test(groups={"regression"}, description="TC_CART_010: Kiểm tra tổng tiền chính xác")
    public void kiemTraTongTien() {
        // 1. Thêm 3 sản phẩm đầu tiên
        inventoryPage.themNSanPhamDauTien(3);
        List<Double> danhSachGia = inventoryPage.layDanhSachGiaSanPham();

        // Tính tổng tiền bằng tay (của 3 món đầu tiên)
        double tongTienTinhTay = danhSachGia.get(0) + danhSachGia.get(1) + danhSachGia.get(2);

        // 2. Vào trang checkout
        inventoryPage.clickVaoGioHang();

        // --- ĐOẠN CODE SỬA LỖI TIMEOUT ---
        // Ép bot Selenium dừng lại 1 giây (1000 milliseconds) để đợi trang Giỏ hàng load xong cái nút Checkout
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---------------------------------

        checkoutPage.clickNutCheckout();
        checkoutPage.nhapThongTinGiaoHang("Kiet", "Vo", "700000");

        // 3. Lấy thông tin tiền bạc từ trang web
        double itemTotal = checkoutPage.layItemTotal();
        double tax = checkoutPage.layTax();
        double total = checkoutPage.layTotal();

        // 4. Các lệnh Assert tính toán độ lệch (delta 0.01 theo yêu cầu)
        Assert.assertEquals(itemTotal, tongTienTinhTay, 0.01, "Lỗi: Item total khác tổng tay");
        Assert.assertEquals(tax, itemTotal * 0.08, 0.01, "Lỗi: Thuế tính sai (Phải là 8%)");
        Assert.assertEquals(total, itemTotal + tax, 0.01, "Lỗi: Total không bằng ItemTotal + Tax");
    }
}