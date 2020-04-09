package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class HeapSort<T> extends AbstractSorter<T> {
    public HeapSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public HeapSort(@NotNull Comparator<T> comparator,
                    @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public HeapSort(@NotNull Comparator<T> comparator,
                    @Nullable Snapshoter<T> snapshoter,
                    @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Heapsort */
        int n = values.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(values, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(values, 0, i);

            heapify(values, i, 0);
        }
    }

    private void heapify(List<T> values, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comparator.compare(values.get(left), values.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && comparator.compare(values.get(right), values.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(values, i, largest);

            heapify(values, n, largest);
        }
    }
}
