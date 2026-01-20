public class Bai3 {
    private int number;

    public Bai3(int number) {
        // Kiểm tra số âm theo logic đề bài [cite: 426-427]
        if (number < 0) {
            // Java dùng IllegalArgumentException thay vì ArgumentException của C#
            throw new IllegalArgumentException("Incorrect Value");
        }
        this.number = number;
    }

    public String convertDecimalToAnother(int radix) {
        if (radix < 2 || radix > 16) {
            throw new IllegalArgumentException("Invalid Radix");
        }

        int n = this.number;

        if (n == 0) return "";

        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            int value = n % radix;
            if (value < 10) {
                sb.append(value);
            } else {
                switch (value) {
                    case 10: sb.append("A"); break;
                    case 11: sb.append("B"); break;
                    case 12: sb.append("C"); break;
                    case 13: sb.append("D"); break;
                    case 14: sb.append("E"); break;
                    case 15: sb.append("F"); break;
                }
            }
            n /= radix;
        }

        return sb.reverse().toString();
    }
}