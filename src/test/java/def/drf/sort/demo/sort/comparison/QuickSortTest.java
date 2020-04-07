package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.metric.IterationCountMetric;
import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.simple.SimpleMetricBucket;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.sort.comparison.QuickSort.PartitionScheme.HOARE;
import static def.drf.sort.demo.sort.comparison.QuickSort.PartitionScheme.LOMUTO;
import static def.drf.sort.demo.utils.AssertUtils.assertNaturalOrder;
import static def.drf.sort.demo.utils.RandomGenerator.newListWithRandomValues;
import static def.drf.sort.demo.utils.TestUtils.*;

public class QuickSortTest {
    private QuickSort<Integer> sort;

    @Before
    public void before() {
        sort = null;
    }

    @Test
    public void testSort_withLomutoPartitionScheme() {
        sort = new QuickSort<Integer>(LOMUTO, Comparator.naturalOrder());

        List<Integer> values = newTestSort();

        sort.sort(values);

        assertNaturalOrder(values);
    }

    @Test
    public void testSort_withHoarePartitionScheme() {
        sort = new QuickSort<Integer>(HOARE, Comparator.naturalOrder());

        List<Integer> values = newTestSort();

        sort.sort(values);

        assertNaturalOrder(values);
    }

    @Test
    public void testIteration_withLomutoPartitionScheme() {
        List<Integer> natural = newNaturalOrder();
        List<Integer> reverse = newReverseOrder();

        SimpleMetricBucket bucketNatural = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();
        SimpleMetricBucket bucketReverse = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();

        sort = new QuickSort<Integer>(LOMUTO, Comparator.naturalOrder(), bucketNatural);

        sort.sort(natural);
        sort.sort(reverse);

        Metric<?> naturalCount = bucketNatural.getMetrics().get(0);
        Metric<?> reverseCount = bucketReverse.getMetrics().get(0);

        System.out.println("Natural count: " + naturalCount.getValue()); // 54
        System.out.println("Reverse count: " + reverseCount.getValue()); // 29

        assertNaturalOrder(natural);
        assertNaturalOrder(reverse);
    }

    @Test
    public void testIteration_withHoarePartitionScheme() {
        List<Integer> natural = newNaturalOrder();
        List<Integer> reverse = newReverseOrder();

        SimpleMetricBucket bucketNatural = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();
        SimpleMetricBucket bucketReverse = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();

        sort = new QuickSort<Integer>(HOARE, Comparator.naturalOrder(), bucketNatural);

        sort.sort(natural);
        sort.sort(reverse);

        Metric<?> naturalCount = bucketNatural.getMetrics().get(0);
        Metric<?> reverseCount = bucketReverse.getMetrics().get(0);

        System.out.println("Natural count: " + naturalCount.getValue()); // 28
        System.out.println("Reverse count: " + reverseCount.getValue()); // 33

        assertNaturalOrder(natural);
        assertNaturalOrder(reverse);
    }

    @Test
    public void testWithBigArray_withLomutoPartitionScheme() {
        final int count = 100;
        double summer = 0;

        for (int i = 0; i < count; i++) {
            List<Integer> values = newListWithRandomValues(1000);

            SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                    .addMetric(new IterationCountMetric())
                    .build();

            sort = new QuickSort<Integer>(LOMUTO, Comparator.naturalOrder(), bucket);

            sort.sort(values);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Swap count: " + summer / count); // ~6034.9
    }

    @Test
    public void testWithBigArray_withHoarePartitionScheme() {
        final int count = 100;
        double summer = 0;

        for (int i = 0; i < count; i++) {
            List<Integer> values = newListWithRandomValues(1000);

            SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                    .addMetric(new IterationCountMetric())
                    .build();

            sort = new QuickSort<Integer>(HOARE, Comparator.naturalOrder(), bucket);

            sort.sort(values);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Swap count: " + summer / count); // ~5717.75
    }
}
