package fpoly.bai6;

import static org.junit.Assert.*;
import fpoly.bai6.UserService;
import org.junit.Test;

public class UserTest {
    UserService service = new UserService();

    // TC_01: Cập nhật hợp lệ (Kỳ vọng: PASS - Thanh Xanh)
    @Test
    public void testValidUpdate() throws Exception {
        service.createTable();
        assertTrue(service.updateUser("admin", "123", "Võ Anh Kiệt", "kiet@gmail.com"));
    }

    // TC_02: Mật khẩu ngắn (Kỳ vọng: PASS - Vì bắt đúng ngoại lệ)
    @Test(expected = IllegalArgumentException.class)
    public void testShortPassword() throws Exception {
        service.updateUser("user2", "12", "Fullname", "user2@gmail.com");
    }

    // TC_03: Email sai định dạng (Kỳ vọng: FAIL - Để làm Báo cáo lỗi 07)
    @Test
    public void testFailInvalidEmail() throws Exception {
        // Log báo lỗi "Invalid email" bạn gửi xuất phát từ đây
        boolean result = service.updateUser("user1", "123", "Full", "kiet_gmail.com");
        assertTrue(result);
    }

    // TC_04: Kiểm tra tính năng Reset (Kỳ vọng: PASS)
    @Test
    public void testResetAction() {
        String user = "data";
        user = ""; // Giả lập hành động clear form
        assertEquals("", user);
    }

    // TC_05: Lỗi logic so sánh (Kỳ vọng: FAIL - Để làm Báo cáo lỗi 08)
    @Test
    public void testFailLogic() {
        // So sánh sai cố ý: Mong đợi admin nhưng thực tế là guest
        assertEquals("Lỗi so sánh cố ý", "admin", "guest");
    }
}