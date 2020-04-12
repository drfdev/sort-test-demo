package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class SelectionSort<T> extends AbstractSorter<T> {
    public SelectionSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public SelectionSort(@NotNull Comparator<T> comparator,
                         @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public SelectionSort(@NotNull Comparator<T> comparator,
                         @Nullable Snapshoter<T> snapshoter,
                         @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Selection_sort */
        int n = values.size();

        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(values.get(j), values.get(min_idx)) < 0) {
                    min_idx = j;
                }
            }

            swap(values, min_idx, i);
        }
    }
}
