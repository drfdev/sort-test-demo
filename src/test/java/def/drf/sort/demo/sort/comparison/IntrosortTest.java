package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.metric.IterationCountMetric;
import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.simple.SimpleMetricBucket;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.utils.AssertUtils.assertNaturalOrder;
import static def.drf.sort.demo.utils.RandomGenerator.newListWithRandomValues;
import static def.drf.sort.demo.utils.TestUtils.newTestSort;

public class IntrosortTest {
    private Introsort<Integer> sort;

    @Test
    public void testSort() {
        sort = new Introsort<Integer>(Comparator.naturalOrder());
        List<Integer> values = newTestSort();

        sort.sort(values);

        assertNaturalOrder(values);
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
            sort = new Introsort<Integer>(Comparator.naturalOrder(), bucket);

            sort.sort(values);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Swap count: " + summer / count); // ~2701.26
    }
}
