package def.drf.sort.demo.sort.others;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.utils.AssertUtils.assertNaturalOrder;
import static def.drf.sort.demo.utils.TestUtils.newTestSort;

public class StoogeSortTest {
    private StoogeSort sort;

    @Before
    public void before() {
        sort = null;
    }

    @Test
    public void testSort() {
        sort = new StoogeSort(Comparator.naturalOrder());
        List<Integer> values = newTestSort();

        sort.sort(values);

        assertNaturalOrder(values);
    }
}
