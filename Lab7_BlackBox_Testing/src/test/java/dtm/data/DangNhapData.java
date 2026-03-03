package dtm.data;

import org.testng.annotations.DataProvider;

public class DangNhapData {
    @DataProvider(name = "du_lieu_dang_nhap")
    public Object[][] getData() {
        return new Object[][]{
                // username, password, ketQuaMongDoi, moTa
                // 1. Tài khoản hợp lệ
                {"standard_user", "secret_sauce", "THÀNH CÔNG", "Đăng nhập thành công với tài khoản chuẩn"},
                {"problem_user", "secret_sauce", "THÀNH CÔNG", "Đăng nhập thành công nhưng UI lỗi"},
                {"performance_glitch_user", "secret_sauce", "THÀNH CÔNG", "Đăng nhập thành công nhưng tải chậm"},
                {"error_user", "secret_sauce", "THÀNH CÔNG", "Đăng nhập thành công nhưng action lỗi"},

                // 2. Tài khoản bị khóa (dữ liệu từ web cung cấp)
                {"locked_out_user", "secret_sauce", "BỊ KHÓA", "Tài khoản bị khóa, báo lỗi locked out"},

                // 3. Tài khoản không tồn tại / Sai thông tin
                {"admin_fake", "123456", "SAI THÔNG TIN", "Tài khoản không tồn tại trong hệ thống"},
                {"standard_user", "sai_pass", "SAI THÔNG TIN", "Đúng user nhưng sai password"},

                // 4. Bỏ trống dữ liệu
                {"", "secret_sauce", "TRỐNG USERNAME", "Bỏ trống trường Username"},
                {"standard_user", "", "TRỐNG PASSWORD", "Bỏ trống trường Password"},
                {"", "", "TRỐNG USERNAME", "Bỏ trống cả Username và Password (Ưu tiên báo lỗi user trước)"},

                // 5. Ký tự đặc biệt & khoảng trắng
                {" standard_user ", "secret_sauce", "SAI THÔNG TIN", "Username chứa khoảng trắng dư thừa"},
                {"user!@#", "secret_sauce", "SAI THÔNG TIN", "Username chứa ký tự đặc biệt"},

                // 6. Giá trị null
                {null, "secret_sauce", "TRỐNG USERNAME", "Truyền giá trị null cho Username"},
                {"standard_user", null, "TRỐNG PASSWORD", "Truyền giá trị null cho Password"}
        };
    }
}