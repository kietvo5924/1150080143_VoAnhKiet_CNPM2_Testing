package dtm;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Epic("E-Commerce Order Management")
@Feature("Order Processor - Calculate Total")
public class OrderProcessorTest {

    private OrderProcessor processor;

    @BeforeMethod
    public void setUp() {
        processor = new OrderProcessor();
    }

    @Test(description = "Path 1 (Baseline): Freeship, no discount")
    @Story("Calculate Normal Order")
    @Description("Test đơn hàng cơ bản trên 500k, không áp mã, freeship")
    @Severity(SeverityLevel.BLOCKER)
    public void testPath1_Baseline() {
        List<Item> items = Arrays.asList(new Item(600_000));
        double total = processor.calculateTotal(items, null, "NORMAL", "COD");
        Assert.assertEquals(total, 600_000.0, 0.01);
    }

    @Test(description = "Path 2: Cart is null -> Exception", expectedExceptions = IllegalArgumentException.class)
    @Story("Handle Empty/Null Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testPath2_EmptyCart_ThrowsException() {
        processor.calculateTotal(null, null, "NORMAL", "COD");
    }

    @Test(description = "Bổ sung Branch D1: Cart is Empty List -> Exception", expectedExceptions = IllegalArgumentException.class)
    @Story("Handle Empty/Null Cart")
    @Description("Truyền vào list rỗng để phủ nhánh isEmpty() của điều kiện D1")
    @Severity(SeverityLevel.CRITICAL)
    public void testBranch_EmptyListCart_ThrowsException() {
        processor.calculateTotal(new ArrayList<>(), null, "NORMAL", "COD");
    }

    @Test(description = "Path 3: Coupon SALE10")
    @Story("Apply Coupons")
    @Severity(SeverityLevel.NORMAL)
    public void testPath3_CouponSale10() {
        List<Item> items = Arrays.asList(new Item(600_000));
        double total = processor.calculateTotal(items, "SALE10", "NORMAL", "COD");
        Assert.assertEquals(total, 540_000.0, 0.01);
    }

    @Test(description = "Path 4: Coupon SALE20 with Shipping Fee")
    @Story("Apply Coupons")
    @Severity(SeverityLevel.NORMAL)
    public void testPath4_CouponSale20_AddShipCOD() {
        List<Item> items = Arrays.asList(new Item(600_000));
        double total = processor.calculateTotal(items, "SALE20", "NORMAL", "COD");
        Assert.assertEquals(total, 500_000.0, 0.01);
    }

    @Test(description = "Path 5: Invalid Coupon", expectedExceptions = IllegalArgumentException.class)
    @Story("Apply Coupons")
    @Severity(SeverityLevel.MINOR)
    public void testPath5_InvalidCoupon_ThrowsException() {
        List<Item> items = Arrays.asList(new Item(600_000));
        processor.calculateTotal(items, "INVALID_CODE", "NORMAL", "COD");
    }

    @Test(description = "Path 6: Member GOLD")
    @Story("Apply Member Discount")
    @Severity(SeverityLevel.NORMAL)
    public void testPath6_MemberGold() {
        List<Item> items = Arrays.asList(new Item(600_000));
        double total = processor.calculateTotal(items, null, "GOLD", "COD");
        Assert.assertEquals(total, 570_000.0, 0.01);
    }

    @Test(description = "Path 7: Member PLATINUM")
    @Story("Apply Member Discount")
    @Severity(SeverityLevel.NORMAL)
    public void testPath7_MemberPlatinum() {
        List<Item> items = Arrays.asList(new Item(600_000));
        double total = processor.calculateTotal(items, null, "PLATINUM", "COD");
        Assert.assertEquals(total, 540_000.0, 0.01);
    }

    @Test(description = "Path 8: Small Order COD")
    @Story("Calculate Shipping Fee")
    @Severity(SeverityLevel.MINOR)
    public void testPath8_SmallOrder_ShipCOD() {
        List<Item> items = Arrays.asList(new Item(400_000));
        double total = processor.calculateTotal(items, null, "NORMAL", "COD");
        Assert.assertEquals(total, 420_000.0, 0.01);
    }

    @Test(description = "Path 9: Small Order Online Payment")
    @Story("Calculate Shipping Fee")
    @Severity(SeverityLevel.MINOR)
    public void testPath9_SmallOrder_ShipOnline() {
        List<Item> items = Arrays.asList(new Item(400_000));
        double total = processor.calculateTotal(items, null, "NORMAL", "VNPAY");
        Assert.assertEquals(total, 430_000.0, 0.01);
    }

    @Test(description = "MC/DC: Empty Coupon String")
    @Story("Apply Coupons")
    @Description("Kiểm tra MC/DC nhánh coupon rỗng, bỏ qua discount")
    @Severity(SeverityLevel.NORMAL)
    public void testMCDC_EmptyCoupon_NoDiscount() {
        List<Item> items = Arrays.asList(new Item(600_000));
        double total = processor.calculateTotal(items, "", "NORMAL", "COD");
        Assert.assertEquals(total, 600_000.0, 0.01);
    }
}