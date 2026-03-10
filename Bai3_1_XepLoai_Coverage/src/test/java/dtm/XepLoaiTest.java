package dtm;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XepLoaiTest {

    // 1. Tạo DataProvider chứa 20 test case
    @DataProvider(name = "xepLoaiData")
    public Object[][] getXepLoaiData() {
        return new Object[][] {
                // Cấu trúc: { "TC ID", diemTB, coThiLai, "Kết quả mong đợi" }
                {"TC_01", -1, false, "Diem khong hop le"},
                {"TC_02", -1, true,  "Diem khong hop le"},
                {"TC_03", 11, false, "Diem khong hop le"},
                {"TC_04", 11, true,  "Diem khong hop le"},

                {"TC_05", 10, false, "Gioi"},
                {"TC_06", 10, true,  "Gioi"},
                {"TC_07",  9, false, "Gioi"},
                {"TC_08",  9, true,  "Gioi"},

                {"TC_09",  8, false, "Kha"},
                {"TC_10",  8, true,  "Kha"},
                {"TC_11",  7, false, "Kha"},
                {"TC_12",  7, true,  "Kha"},

                {"TC_13",  6, false, "Trung Binh"},
                {"TC_14",  6, true,  "Trung Binh"},

                {"TC_15",  5, true,  "Thi lai"},
                {"TC_16",  4, true,  "Thi lai"},
                {"TC_17",  0, true,  "Thi lai"},

                {"TC_18",  5, false, "Yeu - Hoc lai"},
                {"TC_19",  4, false, "Yeu - Hoc lai"},
                {"TC_20",  0, false, "Yeu - Hoc lai"}
        };
    }

    // 2. Hàm @Test sẽ tự động chạy 20 lần dựa trên dữ liệu từ DataProvider
    @Test(dataProvider = "xepLoaiData", description = "Kiểm thử hộp trắng hàm XepLoai với 20 Test Case (100% Branch & Statement Coverage)")
    public void testXepLoaiCoverage(String tcId, int diemTB, boolean coThiLai, String expectedResult) {
        // Thực thi hàm cần test
        String actualResult = XepLoai.xepLoai(diemTB, coThiLai);

        // Assert kết quả và in ra TC ID nếu fail để dễ debug
        Assert.assertEquals(actualResult, expectedResult, "Lỗi logic tại Test Case: " + tcId);

        // In ra console để theo dõi luồng chạy
        System.out.println("Đã pass " + tcId + " -> diemTB: " + diemTB + ", coThiLai: " + coThiLai + " => Kết quả: " + actualResult);
    }
}