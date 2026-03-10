package dtm;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PhoneValidatorTest {

    @DataProvider(name = "phoneData")
    public Object[][] getPhoneData() {
        return new Object[][] {
                {"D1", null, false},
                {"D2", "090123abcd", false},
                {"D3", "+84 9012345", false},
                {"D4", "09012345678", false},
                {"D5", "0201234567", false},
                {"D6_Hople", "0901234567", true},
                {"B1_Hople", "+84 90 123 4567", true},
                {"B2_Rong", "", false},
                {"B3_Space", "   ", false}
        };
    }

    @Test(dataProvider = "phoneData")
    public void testPhoneValidation(String id, String phone, boolean expected) {
        // Lúc này chữ PhoneValidator sẽ bị BÔI ĐỎ báo lỗi (Compile Error)
        boolean actual = PhoneValidator.isValid(phone);
        Assert.assertEquals(actual, expected, "Lỗi tại test case: " + id);
    }
}