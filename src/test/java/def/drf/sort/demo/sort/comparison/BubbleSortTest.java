package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.metric.IterationCountMetric;
import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.simple.SimpleMetricBucket;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static def.drf.sort.demo.utils.RandomGenerator.newListWithRandomValues;
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

    @Test
    public void testIteration() {
        List<Integer> natural = new ArrayList<>() {{
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
        List<Integer> reverse = new ArrayList<>() {{
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

        SimpleMetricBucket bucketNatural = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();
        SimpleMetricBucket bucketReverse = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();

        sort.sort(natural, Comparator.naturalOrder(), bucketNatural);
        sort.sort(reverse, Comparator.naturalOrder(), bucketReverse);

        Metric<?> naturalCount = bucketNatural.getMetrics().get(0);
        Metric<?> reverseCount = bucketReverse.getMetrics().get(0);

        System.out.println("Natural count: " + naturalCount.getValue()); // 0
        System.out.println("Reverse count: " + reverseCount.getValue()); // 45

        assertEquals(Integer.valueOf(1), natural.get(0));
        assertEquals(Integer.valueOf(2), natural.get(1));
        assertEquals(Integer.valueOf(3), natural.get(2));
        assertEquals(Integer.valueOf(4), natural.get(3));
        assertEquals(Integer.valueOf(5), natural.get(4));
        assertEquals(Integer.valueOf(6), natural.get(5));
        assertEquals(Integer.valueOf(7), natural.get(6));
        assertEquals(Integer.valueOf(8), natural.get(7));
        assertEquals(Integer.valueOf(9), natural.get(8));
        assertEquals(Integer.valueOf(10), natural.get(9));

        assertEquals(Integer.valueOf(1), reverse.get(0));
        assertEquals(Integer.valueOf(2), reverse.get(1));
        assertEquals(Integer.valueOf(3), reverse.get(2));
        assertEquals(Integer.valueOf(4), reverse.get(3));
        assertEquals(Integer.valueOf(5), reverse.get(4));
        assertEquals(Integer.valueOf(6), reverse.get(5));
        assertEquals(Integer.valueOf(7), reverse.get(6));
        assertEquals(Integer.valueOf(8), reverse.get(7));
        assertEquals(Integer.valueOf(9), reverse.get(8));
        assertEquals(Integer.valueOf(10), reverse.get(9));
    }

    @Test
    public void testWithBigArray() {
        List<Integer> values = newListWithRandomValues(1000);

        SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();

        sort.sort(values, Comparator.naturalOrder(), bucket);

        Metric<?> counter = bucket.getMetrics().get(0);
        System.out.println("Array size: 1000");
        System.out.println("Iteration count: " + counter.getValue()); // ~250112
    }
}
