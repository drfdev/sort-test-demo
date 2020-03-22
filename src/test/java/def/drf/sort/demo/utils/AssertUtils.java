package def.drf.sort.demo.utils;

import java.util.List;

import static org.junit.Assert.assertEquals;

public final class AssertUtils {
    private AssertUtils() {
    }

    public static void assertNaturalOrder(List<Integer> values) {
        assertEquals(Integer.valueOf(1), values.get(0));
        assertEquals(Integer.valueOf(2), values.get(1));
        assertEquals(Integer.valueOf(3), values.get(2));
        assertEquals(Integer.valueOf(4), values.get(3));
        assertEquals(Integer.valueOf(5), values.get(4));
        assertEquals(Integer.valueOf(6), values.get(5));
        assertEquals(Integer.valueOf(7), values.get(6));
        assertEquals(Integer.valueOf(8), values.get(7));
        assertEquals(Integer.valueOf(9), values.get(8));
        assertEquals(Integer.valueOf(10), values.get(9));
    }
}
