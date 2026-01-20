import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;

public class TestBai5 {

    @Test
    public void testDatHocBong() {
        Bai5.HocVien hv = new Bai5.HocVien("HV01", "Nguyen Van A", "HN", 8.0, 9.0, 10.0);
        Assert.assertTrue(hv.checkHocBong());
    }

    @Test
    public void testTruotDoDiemThap() {
        Bai5.HocVien hv = new Bai5.HocVien("HV02", "Nguyen Van B", "HCM", 7.0, 7.0, 7.0);
        Assert.assertFalse(hv.checkHocBong());
    }

    @Test
    public void testTruotDoDiemLiet() {
        Bai5.HocVien hv = new Bai5.HocVien("HV03", "Nguyen Van C", "Da Nang", 10.0, 10.0, 4.0);
        Assert.assertFalse(hv.checkHocBong());
    }

    @Test
    public void testDauVuaDu() {
        Bai5.HocVien hv = new Bai5.HocVien("HV04", "Le Thi D", "Hue", 9.5, 9.5, 5.0);
        Assert.assertTrue(hv.checkHocBong());
    }

    @Test
    public void testLocDanhSach() {
        Bai5 bai5 = new Bai5();

        Bai5.HocVien hv1 = new Bai5.HocVien("1", "A", "Q1", 9, 9, 9); // Đậu
        Bai5.HocVien hv2 = new Bai5.HocVien("2", "B", "Q2", 6, 6, 6); // Trượt (ĐTB thấp)
        Bai5.HocVien hv3 = new Bai5.HocVien("3", "C", "Q3", 10, 10, 3); // Trượt (Điểm liệt)

        List<Bai5.HocVien> inputList = Arrays.asList(hv1, hv2, hv3);
        List<Bai5.HocVien> outputList = bai5.getDanhSachHocBong(inputList);

        Assert.assertEquals(1, outputList.size());

        Assert.assertEquals("A", outputList.get(0).getHoTen());
    }
}