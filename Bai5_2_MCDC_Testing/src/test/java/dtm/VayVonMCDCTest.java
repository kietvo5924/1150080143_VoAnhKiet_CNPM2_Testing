package dtm;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class VayVonMCDCTest {

    @Test(description = "Base Case (Row 2): Tất cả điều kiện đều đạt mức cơ sở (A=T, B=T, C=T, D=F)")
    public void testMCDC_Base_HopLe() {
        boolean result = VayVon.duDieuKienVay(22, 10000000, true, 600);
        Assert.assertTrue(result, "Lỗi: Khách hàng đủ ĐK cơ sở (Tuổi, Thu nhập, Tài sản) nhưng bị từ chối!");
    }

    @Test(description = "Kiểm tra sự độc lập của Tuổi (Row 10): Tuổi < 22 (A=F)")
    public void testMCDC_TuoiDocLap_ThapHon22() {
        boolean result = VayVon.duDieuKienVay(20, 10000000, true, 600);
        Assert.assertFalse(result, "Lỗi: Khách hàng dưới 22 tuổi vẫn được vay!");
    }

    @Test(description = "Kiểm tra sự độc lập của Thu Nhập (Row 6): Thu nhập < 10tr (B=F)")
    public void testMCDC_ThuNhapDocLap_ThapHon10Tr() {
        boolean result = VayVon.duDieuKienVay(22, 5000000, true, 600);
        Assert.assertFalse(result, "Lỗi: Khách hàng thu nhập dưới 10tr vẫn được duyệt vay!");
    }

    @Test(description = "Kiểm tra sự độc lập của Tài Sản (Row 4): Không có tài sản bảo lãnh (C=F)")
    public void testMCDC_TaiSanDocLap_KhongCo() {
        boolean result = VayVon.duDieuKienVay(22, 10000000, false, 600);
        Assert.assertFalse(result, "Lỗi: Không có tài sản và tín dụng thấp nhưng vẫn được vay!");
    }

    @Test(description = "Kiểm tra sự độc lập của Tín Dụng (Row 3): Không tài sản nhưng tín dụng >= 700 (D=T)")
    public void testMCDC_TinDungDocLap_LonHon700() {
        boolean result = VayVon.duDieuKienVay(22, 10000000, false, 700);
        Assert.assertTrue(result, "Lỗi: Tín dụng tốt (>=700) nhưng bị từ chối vay do không có tài sản!");
    }

    // =========================================================================
    // CÁCH 2: KẾT HỢP VÀO @DataProvider (Đáp ứng yêu cầu 4)
    // =========================================================================

    @DataProvider(name = "mcdcData")
    public Object[][] getMCDCData() {
        return new Object[][] {
                // Tên kịch bản, Tuổi, Thu Nhập, Tài Sản, Tín Dụng, Kết Quả Mong Đợi
                {"Base Case (Đạt)",         22, 10000000, true,  600, true},
                {"Tuổi độc lập (<22)",      20, 10000000, true,  600, false},
                {"Thu nhập độc lập (<10tr)",22, 5000000,  true,  600, false},
                {"Tài sản độc lập (False)", 22, 10000000, false, 600, false},
                {"Tín dụng độc lập (>=700)",22, 10000000, false, 700, true}
        };
    }

    @Test(dataProvider = "mcdcData", description = "Chạy gộp 5 TC MC/DC bằng DataProvider")
    public void testMCDC_VoiDataProvider(String tenTC, int tuoi, double thuNhap, boolean coTaiSan, int tinDung, boolean expected) {
        boolean actual = VayVon.duDieuKienVay(tuoi, thuNhap, coTaiSan, tinDung);
        Assert.assertEquals(actual, expected, "Kiểm thử thất bại tại kịch bản: " + tenTC);
        System.out.println("Đã pass MC/DC: " + tenTC);
    }
}