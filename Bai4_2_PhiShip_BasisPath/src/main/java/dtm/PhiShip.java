package dtm;

public class PhiShip {
    public static double tinhPhiShip(double trongLuong, String vung, boolean laMember) {
        if (trongLuong <= 0) {
            throw new IllegalArgumentException("Trong luong phai lon hon 0");
        }

        double phi = 0;

        // Nhóm điều kiện Vùng miền
        if (vung.equals("noi_thanh")) {
            phi = 15000;
            if (trongLuong > 5) {
                phi += (trongLuong - 5) * 2000;
            }
        } else if (vung.equals("ngoai_thanh")) {
            phi = 25000;
            if (trongLuong > 3) {
                phi += (trongLuong - 3) * 3000;
            }
        } else {
            phi = 50000;
        }

        // Điểm hội tụ kiểm tra phụ phí trọng lượng chung
        if (trongLuong > 2) {
            phi += (trongLuong - 2) * 5000;
        }

        // Điểm hội tụ kiểm tra giảm giá thành viên
        if (laMember) {
            phi = phi * 0.9;
        }

        return phi;
    }
}