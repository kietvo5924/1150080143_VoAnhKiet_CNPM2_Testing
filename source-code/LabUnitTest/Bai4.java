public class Bai4 {

    public static class Diem {
        public double x;
        public double y;

        public Diem(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class HinhChuNhat {
        private Diem topLeft;
        private Diem bottomRight;

        public HinhChuNhat(Diem topLeft, Diem bottomRight) {
            this.topLeft = topLeft;
            this.bottomRight = bottomRight;
        }

        public double tinhDienTich() {
            double width = Math.abs(bottomRight.x - topLeft.x);
            double height = Math.abs(topLeft.y - bottomRight.y);
            return width * height;
        }

        public boolean kiemTraGiaoNhau(HinhChuNhat other) {
            double thisMinX = Math.min(this.topLeft.x, this.bottomRight.x);
            double thisMaxX = Math.max(this.topLeft.x, this.bottomRight.x);
            double thisMinY = Math.min(this.topLeft.y, this.bottomRight.y);
            double thisMaxY = Math.max(this.topLeft.y, this.bottomRight.y);

            double otherMinX = Math.min(other.topLeft.x, other.bottomRight.x);
            double otherMaxX = Math.max(other.topLeft.x, other.bottomRight.x);
            double otherMinY = Math.min(other.topLeft.y, other.bottomRight.y);
            double otherMaxY = Math.max(other.topLeft.y, other.bottomRight.y);

            boolean xOverlap = (thisMinX <= otherMaxX) && (otherMinX <= thisMaxX);
            boolean yOverlap = (thisMinY <= otherMaxY) && (otherMinY <= thisMaxY);

            return xOverlap && yOverlap;
        }
    }
}