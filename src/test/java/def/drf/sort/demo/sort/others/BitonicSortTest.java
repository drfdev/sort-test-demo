package def.drf.sort.demo.sort.others;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BitonicSortTest {
    private BitonicSort sort;

    @Before
    public void before() {
        sort = null;
    }

    @Test
    public void testSort() {
        // 8 = 2 ^ 3
        sort = new BitonicSort(Comparator.naturalOrder());
        List<Integer> values = new ArrayList<>() {{
            add(5);
            add(3);
            add(1);
            add(2);
            add(4);
            add(8);
            add(7);
            add(6);
        }};

        sort.sort(values);

        assertEquals(Integer.valueOf(1), values.get(0));
        assertEquals(Integer.valueOf(2), values.get(1));
        assertEquals(Integer.valueOf(3), values.get(2));
        assertEquals(Integer.valueOf(4), values.get(3));
        assertEquals(Integer.valueOf(5), values.get(4));
        assertEquals(Integer.valueOf(6), values.get(5));
        assertEquals(Integer.valueOf(7), values.get(6));
        assertEquals(Integer.valueOf(8), values.get(7));
    }

    /*
    @Test
    public void testWithBigArray() {
        final int count = 100;
        double summer = 0;

        for (int i = 0; i < count; i++) {
            List<Integer> values = newListWithRandomValues(1000);

            SimpleMetricBucket bucket = SimpleMetricBucket.builder()
                    .addMetric(new IterationCountMetric())
                    .build();
            sort = new BitonicSort(Comparator.naturalOrder(), bucket);

            sort.sort(values);

            Metric<Integer> counter = (Metric<Integer>) bucket.getMetrics().get(0);
            summer += counter.getValue();
        }
        System.out.println("Array size: 1000, times: 100");
        System.out.println("Swap count: " + summer / count); // ~2701.26
    }
     */
}
