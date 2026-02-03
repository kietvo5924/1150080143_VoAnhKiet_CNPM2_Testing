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
        String id = "U" + System.currentTimeMillis();
        boolean result = service.addUnit(id, "Phong Dao Tao", "Mo ta");
        assertTrue(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyName() throws SQLException {
        service.addUnit("U002", "", "Mo ta");
    }
}