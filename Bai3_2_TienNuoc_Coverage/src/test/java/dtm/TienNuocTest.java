package dtm;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TienNuocTest {

    // 1. Tạo DataProvider chứa 18 Test Case đã thiết kế
    @DataProvider(name = "tienNuocData")
    public Object[][] getTienNuocData() {
        return new Object[][] {
                // Cấu trúc: { "TC ID", soM3, "loaiKhachHang", Kết_quả_mong_đợi (kiểu double) }
                {"TC_01", 0,  "dan_cu",     0.0},
                {"TC_02", -1, "ho_ngheo",   0.0},
                {"TC_03", -5, "kinh_doanh", 0.0},

                {"TC_04", 1,  "ho_ngheo",   5000.0},
                {"TC_05", 5,  "ho_ngheo",   25000.0},
                {"TC_06", 25, "ho_ngheo",   125000.0},

                {"TC_07", 1,  "dan_cu",     7500.0},
                {"TC_08", 5,  "dan_cu",     37500.0},
                {"TC_09", 10, "dan_cu",     75000.0},

                {"TC_10", 11, "dan_cu",     108900.0},
                {"TC_11", 15, "dan_cu",     148500.0},
                {"TC_12", 20, "dan_cu",     198000.0},

                {"TC_13", 21, "dan_cu",     239400.0},
                {"TC_14", 25, "dan_cu",     285000.0},

                {"TC_15", 1,  "kinh_doanh", 22000.0},
                {"TC_16", 5,  "kinh_doanh", 110000.0},
                {"TC_17", 30, "kinh_doanh", 660000.0},

                // Trường hợp nhánh Else cuối cùng (khác ho_ngheo và dan_cu)
                {"TC_18", 10, "loai_khac",  220000.0}
        };
    }

    // 2. Hàm @Test thực thi tự động 18 lần
    @Test(dataProvider = "tienNuocData", description = "Kiểm thử tính tiền nước (100% Branch Coverage + BVA/EP)")
    public void testTinhTienNuoc(String tcId, int soM3, String loaiKhachHang, double expectedResult) {

        // Gọi hàm để lấy kết quả thực tế
        double actualResult = TienNuoc.tinhTienNuoc(soM3, loaiKhachHang);

        // Assert kết quả (thêm tham số 0.01 làm sai số chấp nhận cho kiểu double)
        Assert.assertEquals(actualResult, expectedResult, 0.01, "Sai logic tại Test Case: " + tcId);

        // In log ra console để nộp báo cáo
        System.out.println("Đã pass " + tcId + " -> soM3: " + soM3 + ", loaiKhachHang: " + loaiKhachHang + " => Tính ra: " + actualResult + " VNĐ");
    }
}