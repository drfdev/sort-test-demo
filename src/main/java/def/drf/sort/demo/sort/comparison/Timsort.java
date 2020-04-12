package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class Timsort<T> extends AbstractSorter<T> {
    private static final int RUN = 32;

    public Timsort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public Timsort(@NotNull Comparator<T> comparator,
                   @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public Timsort(@NotNull Comparator<T> comparator,
                   @Nullable Snapshoter<T> snapshoter,
                   @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Timsort */
        final int n = values.size();

        for (int i = 0; i < n; i += RUN) {
            insertionSort(values, i, Math.min((i + 31), (n - 1)));
        }

        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                merge(values, left, mid, right);
            }
        }
    }

    private void insertionSort(List<T> values, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            T temp = values.get(i);
            int j = i - 1;
            while (j >= left && comparator.compare(values.get(j), temp) > 0) {
//                values.set(j + 1, values.get(j));
                metric(values, j + 1, values.get(j), j);
                j--;
            }
//            values.set(j + 1, temp);
            metric(values, j + 1, temp, i);
        }
    }

    public void merge(List<T> values, int l, int m, int r) {
        int len1 = m - l + 1;
        int len2 = r - m;

        T[] left = (T[]) new Object[len1];
        T[] right = (T[]) new Object[len2];

        for (int x = 0; x < len1; x++) {
            left[x] = values.get(l + x);
        }
        for (int x = 0; x < len2; x++) {
            right[x] = values.get(m + 1 + x);
        }

        int i = 0;
        int j = 0;
        int k = l;

        while (i < len1 && j < len2) {
            if (comparator.compare(left[i], right[j]) <= 0) {
//                values.set(k, left.get(i));
                metric(values, k, left[i], i);
                i++;
            } else {
//                values.set(k, right.get(j));
                metric(values, k, right[j], j);
                j++;
            }
            k++;
        }

        while (i < len1) {
//            values.set(k, left.get(i));
            metric(values, k, left[i], i);
            k++;
            i++;
        }

        while (j < len2) {
//            values.set(k, right.get(j));
            metric(values, k, right[j], j);
            k++;
            j++;
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
