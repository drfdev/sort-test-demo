package def.drf.sort.demo.sort.noncomparison;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CountingSortTest {
    private CountingSort sort;

    @Before
    public void before() {
        sort = null;
    }

    @Test
    public void testSort() {
        sort = new CountingSort(Comparator.naturalOrder());
        List<Character> values = new ArrayList<>() {{
            add((char) 5);
            add((char) 3);
            add((char) 1);
            add((char) 2);
            add((char) 4);
            add((char) 10);
            add((char) 8);
            add((char) 7);
            add((char) 9);
            add((char) 6);
        }};

        sort.sort(values);

        assertEquals((char) 1, values.get(0).charValue());
        assertEquals((char) 2, values.get(1).charValue());
        assertEquals((char) 3, values.get(2).charValue());
        assertEquals((char) 4, values.get(3).charValue());
        assertEquals((char) 5, values.get(4).charValue());
        assertEquals((char) 6, values.get(5).charValue());
        assertEquals((char) 7, values.get(6).charValue());
        assertEquals((char) 8, values.get(7).charValue());
        assertEquals((char) 9, values.get(8).charValue());
        assertEquals((char) 10, values.get(9).charValue());
    }
}
