package def.drf.sort.demo.utils;

import java.util.ArrayList;
import java.util.List;

public final class TestUtils {
    private TestUtils() {
    }

    public static List<Integer> newTestSort() {
        return new ArrayList<>() {{
            add(5);
            add(3);
            add(1);
            add(2);
            add(4);
            add(10);
            add(8);
            add(7);
            add(9);
            add(6);
        }};
    }

    public static List<Integer> newNaturalOrder() {
        return new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
            add(7);
            add(8);
            add(9);
            add(10);
        }};
    }

    public static List<Integer> newReverseOrder() {
        return new ArrayList<>() {{
            add(10);
            add(9);
            add(8);
            add(7);
            add(6);
            add(5);
            add(4);
            add(3);
            add(2);
            add(1);
        }};
    }
}
