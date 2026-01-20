package com.example.organizationapp.service;

import com.example.organizationapp.entity.Organization;
import com.example.organizationapp.repository.OrganizationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepository repository; // Giả lập Repository

    @InjectMocks
    private OrganizationService service; // Inject Mock vào Service

    // --- NHÓM TEST DỮ LIỆU HỢP LỆ ---

    @Test // TC01
    public void testSave_Valid_FullInfo() {
        Organization org = new Organization("FPT Software", "Hanoi", "0912345678", "contact@fpt.com");

        // Giả lập: Khi gọi check trùng tên -> trả về false (chưa có)
        Mockito.when(repository.existsByOrgName("FPT Software")).thenReturn(false);

        String result = service.saveOrganization(org);

        Assertions.assertEquals("Save successfully", result);
        Assertions.assertTrue(service.isDirectorButtonEnabled); // Nút Director phải bật
    }

    @Test // TC02
    public void testSave_Valid_NameOnly() {
        Organization org = new Organization("Viettel", "", "", "");
        Mockito.when(repository.existsByOrgName("Viettel")).thenReturn(false);

        Assertions.assertEquals("Save successfully", service.saveOrganization(org));
    }

    // --- NHÓM TEST BIÊN (BOUNDARY) ---

    @Test // TC03: Tên 3 ký tự (Min)
    public void testSave_Valid_MinNameLength() {
        Organization org = new Organization("ABC", "", "", "");
        Mockito.when(repository.existsByOrgName("ABC")).thenReturn(false);
        Assertions.assertEquals("Save successfully", service.saveOrganization(org));
    }

    @Test // TC04: Phone 9 số (Min)
    public void testSave_Valid_MinPhone() {
        Organization org = new Organization("ABC Corp", "", "123456789", "");
        Mockito.when(repository.existsByOrgName("ABC Corp")).thenReturn(false);
        Assertions.assertEquals("Save successfully", service.saveOrganization(org));
    }

    @Test // TC05: Phone 12 số (Max)
    public void testSave_Valid_MaxPhone() {
        Organization org = new Organization("ABC Corp", "", "123456789012", "");
        Mockito.when(repository.existsByOrgName("ABC Corp")).thenReturn(false);
        Assertions.assertEquals("Save successfully", service.saveOrganization(org));
    }

    // --- NHÓM TEST KHÔNG HỢP LỆ (INVALID) ---

    @Test
    public void testSave_Invalid_EmptyName() {
        Organization org = new Organization("", "", "", "");
        String result = service.saveOrganization(org);
        Assertions.assertEquals("Error: Organization Name cannot be empty", result);
        Assertions.assertFalse(service.isDirectorButtonEnabled); // Nút Director phải tắt
    }

    @Test
    public void testSave_Invalid_ShortName() {
        Organization org = new Organization("AB", "", "", "");
        Assertions.assertEquals("Error: Organization Name must be between 3 and 255 characters", service.saveOrganization(org));
    }

    @Test
    public void testSave_Invalid_PhoneChar() {
        Organization org = new Organization("Test", "", "0909abc", "");
        Mockito.when(repository.existsByOrgName("Test")).thenReturn(false);
        Assertions.assertEquals("Error: Phone must contains only digits and length 9-12", service.saveOrganization(org));
    }

    @Test
    public void testSave_Invalid_PhoneShort() {
        Organization org = new Organization("Test", "", "12345678", "");
        Mockito.when(repository.existsByOrgName("Test")).thenReturn(false);
        Assertions.assertEquals("Error: Phone must contains only digits and length 9-12", service.saveOrganization(org));
    }

    @Test
    public void testSave_Invalid_PhoneLong() {
        Organization org = new Organization("Test", "", "1234567890123", "");
        Mockito.when(repository.existsByOrgName("Test")).thenReturn(false);
        Assertions.assertEquals("Error: Phone must contains only digits and length 9-12", service.saveOrganization(org));
    }

    @Test
    public void testSave_Invalid_Email() {
        Organization org = new Organization("Test", "", "", "abc.com"); // Thiếu @
        Mockito.when(repository.existsByOrgName("Test")).thenReturn(false);
        Assertions.assertEquals("Error: Invalid Email format", service.saveOrganization(org));
    }

    @Test
    public void testSave_Duplicate_Name() {
        Organization org = new Organization("FPT Software", "", "", "");

        // Giả lập: Repository tìm thấy tên này rồi -> trả về true
        Mockito.when(repository.existsByOrgName("FPT Software")).thenReturn(true);

        String result = service.saveOrganization(org);
        Assertions.assertEquals("Error: Organization Name already exists", result);
        // Quan trọng: Phải đảm bảo hàm save() của repository KHÔNG bao giờ được gọi
        Mockito.verify(repository, Mockito.never()).save(org);
    }

    // --- BỔ SUNG ĐỂ ĐẠT 15+ TEST CASE ---

    @Test // TC13: Trùng tên nhưng khác chữ hoa/thường (Ví dụ: "fpt" trùng "FPT")
    public void testSave_Duplicate_CaseInsensitive() {
        Organization org = new Organization("fpt software", "", "", ""); // Nhập chữ thường

        // Giả lập: Repository tìm thấy tên này (không phân biệt hoa thường)
        // Lưu ý: Logic so sánh thực tế nằm ở DB, nhưng ở đây ta Mock để giả định DB bắt được lỗi này
        Mockito.when(repository.existsByOrgName("fpt software")).thenReturn(true);

        String result = service.saveOrganization(org);
        Assertions.assertEquals("Error: Organization Name already exists", result);
    }

    @Test // TC14: Tên chỉ chứa toàn khoảng trắng (Space) -> Coi như rỗng
    public void testSave_Invalid_NameOnlySpaces() {
        Organization org = new Organization("   ", "", "", ""); // 3 dấu cách

        String result = service.saveOrganization(org);

        // Logic service có .trim() nên sẽ phát hiện ra rỗng
        Assertions.assertEquals("Error: Organization Name cannot be empty", result);
    }

    @Test // TC15: Tên có độ dài tối đa (255 ký tự) -> Biên Max
    public void testSave_Valid_MaxNameLength() {
        String maxName = "A".repeat(255);
        Organization org = new Organization(maxName, "", "", "");

        Mockito.when(repository.existsByOrgName(maxName)).thenReturn(false);

        Assertions.assertEquals("Save successfully", service.saveOrganization(org));
    }

    @Test // TC16: Kiểm tra nút Director vẫn Disable khi Save thất bại (Workflow Test)
    public void testWorkflow_DirectorButtonRemainsDisabled() {
        Organization org = new Organization("", "", "", "");

        service.saveOrganization(org);

        Assertions.assertFalse(service.isDirectorButtonEnabled, "Button should remain disabled on error");
    }
}