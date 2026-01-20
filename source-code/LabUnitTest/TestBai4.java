import org.junit.Test;
import org.junit.Assert;

public class TestBai4 {

    @Test
    public void testTinhDienTich() {
        Bai4.Diem p1 = new Bai4.Diem(0, 10);
        Bai4.Diem p2 = new Bai4.Diem(10, 0);
        Bai4.HinhChuNhat hcn = new Bai4.HinhChuNhat(p1, p2);

        Assert.assertEquals(100.0, hcn.tinhDienTich(), 0.0001);
    }

    @Test
    public void testCoGiaoNhau() {
        Bai4.HinhChuNhat hcn1 = new Bai4.HinhChuNhat(
                new Bai4.Diem(0, 5),
                new Bai4.Diem(5, 0)
        );

        Bai4.HinhChuNhat hcn2 = new Bai4.HinhChuNhat(
                new Bai4.Diem(3, 8),
                new Bai4.Diem(8, 3)
        );

        Assert.assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
    }

    @Test
    public void testKhongGiaoNhau() {
        Bai4.HinhChuNhat hcn1 = new Bai4.HinhChuNhat(
                new Bai4.Diem(0, 5),
                new Bai4.Diem(5, 0)
        );

        Bai4.HinhChuNhat hcn2 = new Bai4.HinhChuNhat(
                new Bai4.Diem(10, 15),
                new Bai4.Diem(15, 10)
        );

        Assert.assertFalse(hcn1.kiemTraGiaoNhau(hcn2));
    }

    @Test
    public void testLongNhau() {
        // HCN Lớn: (0, 10) -> (10, 0)
        Bai4.HinhChuNhat hcn1 = new Bai4.HinhChuNhat(
                new Bai4.Diem(0, 10),
                new Bai4.Diem(10, 0)
        );

        // HCN Bé nằm giữa: (2, 8) -> (8, 2)
        Bai4.HinhChuNhat hcn2 = new Bai4.HinhChuNhat(
                new Bai4.Diem(2, 8),
                new Bai4.Diem(8, 2)
        );

        Assert.assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
    }
}