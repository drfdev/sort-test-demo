package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class InsertionSort<T> extends AbstractSorter<T> {
    public InsertionSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public InsertionSort(@NotNull Comparator<T> comparator,
                         @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public InsertionSort(@NotNull Comparator<T> comparator,
                         @Nullable Snapshoter<T> snapshoter,
                         @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Insertion_sort */
        int n = values.size();
        for (int i = 1; i < n; ++i) {
            T key = values.get(i);
            final int keyIndex = i;
            int j = i - 1;

            while (j >= 0 && comparator.compare(values.get(j), key) > 0) {
//                values.set(j + 1, values.get(j));
                metric(values, j + 1, values.get(j), keyIndex);
                j = j - 1;
            }
//            values.set(j + 1, key);
            metric(values, j + 1, key, keyIndex);
        }
    }

    private void metric(List<T> values, int index, T value, int valueIndex) {
        if (metrics != null) {
            metrics.metricsData(value, values.get(index), valueIndex, index);
        }
        values.set(index, value);
        if (snapshoter != null) {
            snapshoter.snapshot(values);
        }
    }
}
