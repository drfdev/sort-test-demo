package def.drf.sort.demo.sort.comparison;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.sort.comparison.MergeSort.Implementation.TOP_DOWN;
import static def.drf.sort.demo.utils.AssertUtils.assertNaturalOrder;
import static def.drf.sort.demo.utils.TestUtils.newTestSort;

public class MergeSortTest {
    private MergeSort sort;

    @Before
    public void before() {
        sort = null;
    }

    @Test
    public void testSort_withTopDownImplementation() {
        sort = new MergeSort(TOP_DOWN);

        List<Integer> values = newTestSort();

        sort.sort(values, Comparator.naturalOrder());

        assertNaturalOrder(values);
    }
}
