import java.util.List;

public class Bai2 {
    private int n;
    private List<Integer> a;

    public Bai2(int n, List<Integer> a) {
        if (n < 0 || a.size() != n + 1) {
            throw new IllegalArgumentException("Invalid Data");
        }
        this.n = n;
        this.a = a;
    }

    public int Cal(double x) {
        int result = 0;
        for (int i = 0; i <= this.n; i++) {
            // Math.pow trả về double nên cần ép kiểu về int theo đề bài
            result += (int) (a.get(i) * Math.pow(x, i));
        }
        return result;
    }
}