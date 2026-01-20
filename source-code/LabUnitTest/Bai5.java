import java.util.ArrayList;
import java.util.List;

public class Bai5 {

    public static class HocVien {
        private String maHocVien;
        private String hoTen;
        private String queQuan;
        private double diem1;
        private double diem2;
        private double diem3;

        public HocVien(String maHocVien, String hoTen, String queQuan, double d1, double d2, double d3) {
            this.maHocVien = maHocVien;
            this.hoTen = hoTen;
            this.queQuan = queQuan;
            this.diem1 = d1;
            this.diem2 = d2;
            this.diem3 = d3;
        }

        public double getDiemTrungBinh() {
            return (diem1 + diem2 + diem3) / 3.0;
        }

        public boolean checkHocBong() {
            double dtb = getDiemTrungBinh();
            return dtb >= 8.0 && diem1 >= 5.0 && diem2 >= 5.0 && diem3 >= 5.0;
        }

        public String getHoTen() {
            return hoTen;
        }
    }

    public List<HocVien> getDanhSachHocBong(List<HocVien> danhSachGoc) {
        List<HocVien> result = new ArrayList<>();
        for (HocVien hv : danhSachGoc) {
            if (hv.checkHocBong()) {
                result.add(hv);
            }
        }
        return result;
    }
}