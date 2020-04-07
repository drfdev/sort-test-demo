package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class Introsort<T> extends AbstractSorter<T> {
    public Introsort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public Introsort(@NotNull Comparator<T> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public Introsort(@NotNull Comparator<T> comparator,
                     @Nullable Snapshoter<T> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Introsort */
        int depth = (int) (2 * Math.log(values.size()));
        introsort(values, depth, 0, values.size() - 1);
    }

    private void introsort(List<T> values, int depth, int start, int end) {
        int length = values.size();
        if (length <= 1) {
            return;
        } else if (depth == 0) {
            heapSort(values, start, end);
        } else {
            if (start >= end) {
                return;
            }
            T pivot = values.get((start + end) / 2);
            int index = partition(values, start, end, pivot);
            introsort(values, depth - 1, start, index - 1);
            introsort(values, depth - 1, index, end);
        }
    }

    private void heapSort(List<T> values, int start, int end) {
        for (int i = end / 2 - 1; i >= start; i--) {
            heapify(values, end, i);
        }
        for (int i = end - 1; i >= start; i--) {
            swap(values, start, i);
            heapify(values, i, start);
        }
    }

    private void heapify(List<T> values, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && comparator.compare(values.get(l), values.get(largest)) > 0) {
            largest = l;
        }
        if (r < n && comparator.compare(values.get(r), values.get(largest)) > 0) {
            largest = r;
        }
        if (largest != i) {
            swap(values, i, largest);
            heapify(values, n, largest);
        }
    }

    private int partition(List<T> values, int start, int end, T pivot) {
        while (start <= end) {
            while (comparator.compare(values.get(start), pivot) < 0) {
                start++;
            }
            while (comparator.compare(values.get(end), pivot) > 0) {
                end--;
            }
            if (start <= end) {
                swap(values, start, end);
                start++;
                end--;
            }
        }
        return start;
    }
}
