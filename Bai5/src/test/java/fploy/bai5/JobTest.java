package fpoly.bai5;

import static org.junit.Assert.*;

import fploy.bai5.JobService;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;

public class JobTest {
    JobService service = new JobService();

    @Before
    public void setUp() throws SQLException {
        service.createTable();
    }

    // TC01: Job Title hợp lệ (Kỳ vọng: PASS)
    @Test
    public void testValidJob() throws SQLException {
        String title = "Dev_" + System.currentTimeMillis();
        assertTrue(service.addJob(title, "Description", 500, "Note"));
    }

    // TC02: Job Title rỗng (Kỳ vọng: PASS vì bắt được lỗi đúng)
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyTitle() throws SQLException {
        service.addJob("", "Desc", 100, "Note");
    }

    // TC03: Job Title quá dài (Kỳ vọng: PASS vì bắt được lỗi đúng)
    @Test(expected = IllegalArgumentException.class)
    public void testLongTitle() throws SQLException {
        String longTitle = "A".repeat(101);
        service.addJob(longTitle, "Desc", 100, "Note");
    }

    // TC04: File size quá lớn (Cố tình để FAIL - Thiếu expected)
    @Test
    public void testFailFileSize() throws SQLException {
        // Hàm ném lỗi nhưng không khai báo expected -> JUnit báo Đỏ
        service.addJob("Tester", "Desc", 2048, "Note");
    }

    // TC05: Note quá dài (Cố tình để FAIL - So sánh sai logic)
    @Test
    public void testFailNoteLogic() {
        String note = "Note content";
        // Cố tình fail để kiểm tra báo cáo
        assertEquals("Lỗi cố ý: Mong đợi chuỗi rỗng", "", note);
    }
}