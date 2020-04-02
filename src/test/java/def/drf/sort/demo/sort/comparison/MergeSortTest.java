package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.metric.IterationCountMetric;
import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.simple.SimpleMetricBucket;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.sort.comparison.MergeSort.Implementation.BOTTOM_UP;
import static def.drf.sort.demo.sort.comparison.MergeSort.Implementation.TOP_DOWN;
import static def.drf.sort.demo.utils.AssertUtils.assertNaturalOrder;
import static def.drf.sort.demo.utils.RandomGenerator.newListWithRandomValues;
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

    @Test
    public void testSort_withBottomUpImplementation() {
        sort = new MergeSort(BOTTOM_UP);

        List<Integer> values = newTestSort();

        sort.sort(values, Comparator.naturalOrder());

        assertNaturalOrder(values);
    }

    @Test
    public void testWithBigArray_withTopDownImplementation() {
        sort = new MergeSort(TOP_DOWN);

        final int count = 100;
        double summer = 0;

        for (int i = 0; i < count; i++) {
            List<Integer> values = newListWithRandomValues(1000);

            SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                    .addMetric(new IterationCountMetric())
                    .build();

            sort.sort(values, Comparator.naturalOrder(), bucket);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Iteration count: " + summer / count); // ~9976.0
    }

    @Test
    public void testWithBigArray_withBottomUpImplementation() {
        sort = new MergeSort(BOTTOM_UP);

        final int count = 100;
        double summer = 0;

        for (int i = 0; i < count; i++) {
            List<Integer> values = newListWithRandomValues(1000);

            SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                    .addMetric(new IterationCountMetric())
                    .build();

            sort.sort(values, Comparator.naturalOrder(), bucket);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Iteration count: " + summer / count); // ~20000.0
    }
}
