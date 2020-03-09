package def.drf.sort.demo.sort.comparison;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BubbleSortTest {
    private BubbleSort sort = new BubbleSort();

    @Test
    public void testSort() {
        List<Integer> values = new ArrayList<>() {{
            add(5);
            add(3);
            add(1);
            add(2);
            add(4);
        }};

        sort.sort(values, Comparator.naturalOrder());

        assertEquals(Integer.valueOf(1), values.get(0));
        assertEquals(Integer.valueOf(2), values.get(1));
        assertEquals(Integer.valueOf(3), values.get(2));
        assertEquals(Integer.valueOf(4), values.get(3));
        assertEquals(Integer.valueOf(5), values.get(4));
    }
}
