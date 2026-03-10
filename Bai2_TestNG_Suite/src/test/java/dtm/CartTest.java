package dtm;
import org.testng.annotations.Test;

public class CartTest {
    @Test(groups = {"smoke", "regression"}, description = "Thêm sản phẩm vào giỏ")
    public void testAddProduct() {
        System.out.println("-> CartTest: Chạy testAddProduct (Nhóm: smoke, regression)");
    }

    @Test(groups = {"regression"}, description = "Xóa sản phẩm khỏi giỏ")
    public void testRemoveProduct() {
        System.out.println("-> CartTest: Chạy testRemoveProduct (Nhóm: regression)");
    }
}