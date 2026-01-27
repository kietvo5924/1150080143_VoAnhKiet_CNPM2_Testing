package fpoly.bai5;

import fpoly.lab5.CustomerService;
import fpoly.lab5.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CustomerServiceTest {

    private CustomerService service;

    private final String VALID_ID = "KH0099";
    private final String VALID_NAME = "Nguyen Van Test";
    private final String VALID_EMAIL = "test99@email.com";
    private final String VALID_PHONE = "0912345678";
    private final String VALID_ADDR = "123 Hanoi Street";
    private final String VALID_PASS = "password123";
    private final String VALID_DOB = "01/01/2000";

    @Before
    public void setUp() {
        service = new CustomerService();
        DatabaseConnection.initDB();
        service.deleteUser(VALID_ID);
        service.deleteUser("EXIST01");
    }

    // --- NHÓM 1: HAPPY PATH ---
    @Test // TC_01
    public void testRegisterSuccess() {
        String result = service.validateAndRegister(
                VALID_ID, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Đăng ký tài khoản thành công!", result);
    }

    // --- NHÓM 2: VALIDATION MÃ KHÁCH HÀNG ---
    @Test // TC_02
    public void testIdEmpty() {
        String result = service.validateAndRegister(
                "", VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Mã Khách Hàng là bắt buộc.", result);
    }

    @Test // TC_03 (Ngắn)
    public void testIdTooShort() {
        String result = service.validateAndRegister(
                "ABC12", VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Mã Khách Hàng phải từ 6 đến 10 ký tự.", result);
    }

    @Test // TC_05 (Ký tự đặc biệt)
    public void testIdSpecialChars() {
        String result = service.validateAndRegister(
                "User@123", VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Mã Khách Hàng chỉ được chứa chữ cái và số.", result);
    }

    @Test // TC_06 (Trùng lặp)
    public void testIdDuplicate() {
        // Lần 1: Đăng ký thành công
        service.validateAndRegister(
                "EXIST01", VALID_NAME, "mail1@test.com", VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );

        // Lần 2: Đăng ký lại đúng mã đó -> Mong đợi lỗi
        String result = service.validateAndRegister(
                "EXIST01", "Ten Khac", "mail2@test.com", "0987654321", "HCM",
                VALID_PASS, VALID_PASS, true, "Nu", VALID_DOB
        );
        assertEquals("Mã Khách Hàng đã tồn tại, vui lòng chọn mã khác.", result);
    }

    // --- NHÓM 3: VALIDATION EMAIL ---
    @Test // TC_11
    public void testEmailInvalidFormat() {
        String result = service.validateAndRegister(
                VALID_ID, VALID_NAME, "nguyenvana.gmail", VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Vui lòng nhập đúng định dạng email (vd: abc@email.com).", result);
    }

    // --- NHÓM 4: VALIDATION SỐ ĐIỆN THOẠI ---
    @Test // TC_15 (Không bắt đầu bằng 0)
    public void testPhoneNoZeroStart() {
        String result = service.validateAndRegister(
                VALID_ID, VALID_NAME, VALID_EMAIL, "9123456789", VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Số điện thoại phải bắt đầu bằng số 0.", result);
    }

    @Test // TC_14 (Chứa chữ)
    public void testPhoneWithLetters() {
        String result = service.validateAndRegister(
                VALID_ID, VALID_NAME, VALID_EMAIL, "0987abc123", VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", VALID_DOB
        );
        assertEquals("Số điện thoại chỉ được phép nhập số.", result);
    }

    // --- NHÓM 5: TUỔI VÀ ĐIỀU KHOẢN ---
    @Test // TC_23 (Dưới 18 tuổi)
    public void testUnderAge() {
        // Giả sử năm nay 2026, sinh năm 2010 là mới 16 tuổi
        String dobUnder18 = "01/01/2010";
        String result = service.validateAndRegister(
                VALID_ID, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, true, "Nam", dobUnder18
        );
        assertEquals("Bạn phải đủ 18 tuổi để đăng ký.", result);
    }

    @Test // TC_24 (Không đồng ý điều khoản)
    public void testTosNotAgreed() {
        String result = service.validateAndRegister(
                VALID_ID, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_ADDR,
                VALID_PASS, VALID_PASS, false, "Nam", VALID_DOB
        );
        assertEquals("Bạn phải đồng ý với các điều khoản dịch vụ.", result);
    }
}