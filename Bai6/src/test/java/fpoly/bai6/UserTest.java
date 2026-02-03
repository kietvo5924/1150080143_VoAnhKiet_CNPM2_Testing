package fploy.bai6;

import static org.junit.Assert.*;

import fpoly.bai6.UserService;
import org.junit.Test;

public class UserTest {
    UserService service = new UserService();

    // TC01: Cập nhật hợp lệ (Kỳ vọng: PASS)
    @Test
    public void testValidUpdate() throws Exception {
        service.createTable();
        assertTrue(service.updateUser("admin", "123", "Van Teo", "admin@gmail.com"));
    }

    // TC02: Mật khẩu ngắn (Kỳ vọng: PASS vì bắt được lỗi đúng)
    @Test(expected = IllegalArgumentException.class)
    public void testShortPassword() throws Exception {
        service.updateUser("user2", "12", "Fullname", "user2@gmail.com");
    }

    // TC03: Email sai (Cố tình để FAIL để bạn thấy lỗi trong JUnit)
    @Test
    public void testFailInvalidEmail() throws Exception {
        // Hàm sẽ ném lỗi, nhưng ta dùng assertTrue(result) mà không có expected
        // Kết quả: JUnit sẽ báo Đỏ (Error/Fail)
        boolean result = service.updateUser("user1", "123", "Full", "abc.com");
        assertTrue(result);
    }

    // TC04: Username rỗng (Kỳ vọng: PASS vì bắt được lỗi đúng)
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyUsername() throws Exception {
        service.updateUser("", "123", "Fullname", "email@gmail.com");
    }
}