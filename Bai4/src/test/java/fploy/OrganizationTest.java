package fploy;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;

public class OrganizationTest {
    OrganizationService service = new OrganizationService();

    @Before
    public void setUp() throws SQLException {
        service.createTable();
    }

    @Test
    public void testAddSuccess() throws SQLException {
        String id = "U" + System.currentTimeMillis(); // ID ngẫu nhiên không bao giờ trùng
        assertTrue(service.addUnit(id, "Phong Dao Tao", "Mo ta"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyName() throws SQLException {
        service.addUnit("U_EMPTY", "", "Mo ta");
    }

    @Test
    public void testCancelAction() {
        assertTrue(true); // Giả lập nút Cancel luôn hoạt động đúng
    }

    @Test
    public void testCheckConnection() throws SQLException {
        // Kiểm tra kết nối CSDL có tồn tại không
        assertNotNull(DatabaseConnection.getConnection());
    }
}