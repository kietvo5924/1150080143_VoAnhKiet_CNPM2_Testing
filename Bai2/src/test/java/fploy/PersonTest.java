package fploy;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersonTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    // Cách dùng ExpectedException Rule
    @Test
    public void testExpectedException() {
        exception.expect(IllegalArgumentException.class);
        new Person("Fpoly", -1);
    }

    // Cách dùng @Test(expected)
    @Test(expected = IllegalArgumentException.class)
    public void testExpectedException2() {
        new Person("Fpoly", -1);
    }

    // Cách dùng khối try-catch
    @Test
    public void testExpectedException3() {
        try {
            new Person("Fpoly", -1);
            fail("Should have thrown an IllegalArgumentException because age is invalid!");
        } catch (IllegalArgumentException e) {
        }
    }
}