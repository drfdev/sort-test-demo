package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.metric.IterationCountMetric;
import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.simple.SimpleMetricBucket;
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

    @Test
    public void testIteration() {
        List<Integer> best = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};
        List<Integer> worst = new ArrayList<>() {{
            add(5);
            add(4);
            add(3);
            add(2);
            add(1);
        }};

        SimpleMetricBucket bucketBest = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();
        SimpleMetricBucket bucketWorst = SimpleMetricBucket.builder()
                .addMetric(new IterationCountMetric())
                .build();

        sort.sort(best, Comparator.naturalOrder(), bucketBest);
        sort.sort(worst, Comparator.naturalOrder(), bucketWorst);

        Metric<?> bestCount = bucketBest.getMetrics().get(0);
        Metric<?> worstCount = bucketWorst.getMetrics().get(0);

        System.out.println("Best count: " + bestCount.getValue());
        System.out.println("Worst count: " + worstCount.getValue());
    }
}
