package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PhiShipTest {

    @Test(description = "Path 1 (Baseline): Trọng lượng nhẹ, nội thành, không member")
    public void testPath1_Baseline() {
        double actual = PhiShip.tinhPhiShip(1, "noi_thanh", false);
        Assert.assertEquals(actual, 15000.0, 0.01, "Sai logic Path 1");
    }

    @Test(description = "Path 2: Trọng lượng âm (Lật D1) -> Ném Exception", expectedExceptions = IllegalArgumentException.class)
    public void testPath2_TrongLuongAm_Exception() {
        // TestNG sẽ tự động PASS nếu hàm ném ra IllegalArgumentException
        PhiShip.tinhPhiShip(-1, "noi_thanh", false);
    }

    @Test(description = "Path 3: Nội thành, trọng lượng > 5 (Lật D3)")
    public void testPath3_NoiThanh_SieuNang() {
        // Phí: 15k + (6-5)*2k + (6-2)*5k = 37k
        double actual = PhiShip.tinhPhiShip(6, "noi_thanh", false);
        Assert.assertEquals(actual, 37000.0, 0.01, "Sai logic Path 3");
    }

    @Test(description = "Path 4: Nội thành, trọng lượng > 2 và <= 5 (Lật D6)")
    public void testPath4_NoiThanh_NangVua() {
        // Phí: 15k + (3-2)*5k = 20k
        double actual = PhiShip.tinhPhiShip(3, "noi_thanh", false);
        Assert.assertEquals(actual, 20000.0, 0.01, "Sai logic Path 4");
    }

    @Test(description = "Path 5: Nội thành, trọng lượng nhẹ, LÀ Member (Lật D7)")
    public void testPath5_NoiThanh_LaMember() {
        // Phí: 15k * 0.9 = 13.5k
        double actual = PhiShip.tinhPhiShip(1, "noi_thanh", true);
        Assert.assertEquals(actual, 13500.0, 0.01, "Sai logic Path 5");
    }

    @Test(description = "Path 6: Ngoại thành, trọng lượng nhẹ (Lật D2)")
    public void testPath6_NgoaiThanh_Nhe() {
        // Phí: 25k
        double actual = PhiShip.tinhPhiShip(1, "ngoai_thanh", false);
        Assert.assertEquals(actual, 25000.0, 0.01, "Sai logic Path 6");
    }

    @Test(description = "Path 7: Ngoại thành, trọng lượng > 3 (Lật D5)")
    public void testPath7_NgoaiThanh_Nang() {
        // Phí: 25k + (4-3)*3k + (4-2)*5k = 38k
        double actual = PhiShip.tinhPhiShip(4, "ngoai_thanh", false);
        Assert.assertEquals(actual, 38000.0, 0.01, "Sai logic Path 7");
    }

    @Test(description = "Path 8: Tỉnh khác, trọng lượng nhẹ (Lật D4)")
    public void testPath8_TinhKhac_Nhe() {
        // Phí: 50k
        double actual = PhiShip.tinhPhiShip(1, "tinh_khac", false);
        Assert.assertEquals(actual, 50000.0, 0.01, "Sai logic Path 8");
    }
}