package def.drf.sort.demo.sort.noncomparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class CountingSort extends AbstractSorter<Character> {
    private static final int RANGE = 256;

    public CountingSort(@NotNull Comparator<Character> comparator) {
        super(comparator);
    }

    public CountingSort(@NotNull Comparator<Character> comparator,
                        @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public CountingSort(@NotNull Comparator<Character> comparator,
                        @Nullable Snapshoter<Character> snapshoter,
                        @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Character> values) {
        /* https://en.wikipedia.org/wiki/Counting_sort */
        int range = RANGE;
        int n = values.size();

        char output[] = new char[n];

        int count[] = new int[range];
        for (int i = 0; i < range; ++i) {
            count[i] = 0;
        }

        for (int i = 0; i < n; ++i) {
            ++count[values.get(i)];
        }

        for (int i = 1; i < range; ++i) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[values.get(i)] - 1] = values.get(i);
            --count[values.get(i)];
        }

        for (int i = 0; i < n; ++i) {
            values.set(i, output[i]);
        }
    }
}
