import org.junit.Test;
import org.junit.Assert;

public class TestBai3 {

    @Test
    public void testConvertToBinary() {
        Bai3 bai3 = new Bai3(10);
        String expected = "1010";
        String actual = bai3.convertDecimalToAnother(2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConvertToHex() {
        Bai3 bai3 = new Bai3(255);
        String expected = "FF";
        String actual = bai3.convertDecimalToAnother(16);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHexCharMapping() {
        Bai3 bai3 = new Bai3(12);
        Assert.assertEquals("C", bai3.convertDecimalToAnother(16));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeInput() {
        new Bai3(-100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRadixLarge() {
        Bai3 bai3 = new Bai3(10);
        bai3.convertDecimalToAnother(18);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRadixSmall() {
        Bai3 bai3 = new Bai3(10);
        bai3.convertDecimalToAnother(1);
    }
}