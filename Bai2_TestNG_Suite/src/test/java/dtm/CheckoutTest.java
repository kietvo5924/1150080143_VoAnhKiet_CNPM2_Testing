package dtm;
import org.testng.annotations.Test;

public class CheckoutTest {
    @Test(groups = {"smoke", "regression"}, description = "Thanh toán hợp lệ")
    public void testCheckoutValid() {
        System.out.println("-> CheckoutTest: Chạy testCheckoutValid (Nhóm: smoke, regression)");
    }

    @Test(groups = {"regression"}, description = "Thanh toán thẻ hết hạn")
    public void testCheckoutInvalidCard() {
        System.out.println("-> CheckoutTest: Chạy testCheckoutInvalidCard (Nhóm: regression)");
    }
}