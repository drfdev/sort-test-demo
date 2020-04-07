package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.metric.IterationCountMetric;
import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.simple.SimpleMetricBucket;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.utils.AssertUtils.assertNaturalOrder;
import static def.drf.sort.demo.utils.RandomGenerator.newListWithRandomValues;
import static def.drf.sort.demo.utils.TestUtils.*;

public class BubbleSortTest {
    private BubbleSort<Integer> sort;

    @Test
    public void testSort() {
        sort = new BubbleSort<Integer>(Comparator.naturalOrder());
        List<Integer> values = newTestSort();

        sort.sort(values);

        assertNaturalOrder(values);
    }

    @Test
    public void testIteration() {
        List<Integer> natural = newNaturalOrder();
        List<Integer> reverse = newReverseOrder();

        SimpleMetricBucket bucketNatural = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();
        SimpleMetricBucket bucketReverse = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();
        sort = new BubbleSort<Integer>(Comparator.naturalOrder(), bucketNatural);

        sort.sort(natural);
        sort.sort(reverse);

        Metric<?> naturalCount = bucketNatural.getMetrics().get(0);
        Metric<?> reverseCount = bucketReverse.getMetrics().get(0);

        System.out.println("Natural count: " + naturalCount.getValue()); // 0
        System.out.println("Reverse count: " + reverseCount.getValue()); // 45

        assertNaturalOrder(natural);
        assertNaturalOrder(reverse);
    }

    @Test
    public void testWithBigArray() {
        final int count = 100;
        double summer = 0;

        for (int i = 0; i < count; i++) {
            List<Integer> values = newListWithRandomValues(1000);

            SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                    .addMetric(new IterationCountMetric())
                    .build();
            sort = new BubbleSort<Integer>(Comparator.naturalOrder(), bucket);

            sort.sort(values);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Swap count: " + summer / count); // ~249453.17
    }
}
