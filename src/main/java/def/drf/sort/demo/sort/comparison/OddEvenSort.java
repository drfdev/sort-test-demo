package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class OddEvenSort<T> extends AbstractSorter<T> {
    public OddEvenSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public OddEvenSort(@NotNull Comparator<T> comparator,
                       @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public OddEvenSort(@NotNull Comparator<T> comparator,
                       @Nullable Snapshoter<T> snapshoter,
                       @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Odd%E2%80%93even_sort */
        int n = values.size();
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            int temp = 0;

            for (int i = 1; i <= n - 2; i = i + 2) {
                if (comparator.compare(values.get(i), values.get(i + 1)) > 0) {
                    swap(values, i, i + 1);
                    isSorted = false;
                }
            }

            for (int i = 0; i <= n - 2; i = i + 2) {
                if (comparator.compare(values.get(i), values.get(i + 1)) > 0) {
                    swap(values, i, i + 1);
                    isSorted = false;
                }
            }
        }
    }

}
