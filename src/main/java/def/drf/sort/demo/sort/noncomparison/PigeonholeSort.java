package def.drf.sort.demo.sort.noncomparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PigeonholeSort extends AbstractSorter<Integer> {
    public PigeonholeSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public PigeonholeSort(@NotNull Comparator<Integer> comparator,
                          @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public PigeonholeSort(@NotNull Comparator<Integer> comparator,
                          @Nullable Snapshoter<Integer> snapshoter,
                          @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Pigeonhole_sort */
        int n = values.size();

        int min = values.get(0);
        int max = values.get(0);
        int range, i, j, index;

        for (int a = 0; a < n; a++) {
            if (comparator.compare(values.get(a), max) > 0) {
                max = values.get(a);
            }
            if (comparator.compare(values.get(a), min) < 0) {
                min = values.get(a);
            }
        }

        range = max - min + 1;
        int[] phole = new int[range];
        Arrays.fill(phole, 0);

        for (i = 0; i < n; i++) {
            phole[values.get(i) - min]++;
        }


        index = 0;

        for (j = 0; j < range; j++) {
            while (phole[j]-- > 0) {
                values.set(index++, j + min);
//                setByIndex(values, index++, j + min, j);
            }
        }

        // metrics ??
    }
}
