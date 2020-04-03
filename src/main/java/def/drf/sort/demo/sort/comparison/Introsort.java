package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.Sorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class Introsort implements Sorter {
    @Override
    public <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter, @Nullable MetricBucket metrics) {
        /* https://en.wikipedia.org/wiki/Introsort */
        int depth = (int) (2 * Math.log(values.size()));
        introsort(values, depth, 0, values.size() - 1, comparator, snapshoter, metrics);
    }

    private <T> void introsort(List<T> values, int depth, int start, int end,
                               Comparator<T> comparator, Snapshoter<T> snapshoter, MetricBucket metrics) {
        int length = values.size();
        if (length <= 1) {
            return;
        } else if (depth == 0) {
            heapSort(values, start, end,
                    comparator, snapshoter, metrics);
        } else {
            if (start >= end) {
                return;
            }
            T pivot = values.get((start + end) / 2);
            int index = partition(values, start, end, pivot,
                    comparator, snapshoter, metrics);
            introsort(values, depth - 1, start, index - 1,
                    comparator, snapshoter, metrics);
            introsort(values, depth - 1, index, end,
                    comparator, snapshoter, metrics);
        }
    }

    private <T> void heapSort(List<T> values, int start, int end,
                              Comparator<T> comparator, Snapshoter<T> snapshoter, MetricBucket metrics) {
        for (int i = end / 2 - 1; i >= start; i--) {
            heapify(values, end, i,
                    comparator, snapshoter, metrics);
        }
        for (int i = end - 1; i >= start; i--) {
            swap(values, start, i, snapshoter, metrics);
            heapify(values, i, start,
                    comparator, snapshoter, metrics);
        }
    }

    private <T> void heapify(List<T> values, int n, int i,
                             Comparator<T> comparator, Snapshoter<T> snapshoter, MetricBucket metrics) {
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
            swap(values, i, largest, snapshoter, metrics);
            heapify(values, n, largest,
                    comparator, snapshoter, metrics);
        }
    }

    private <T> int partition(List<T> values, int start, int end, T pivot,
                            Comparator<T> comparator, Snapshoter<T> snapshoter, MetricBucket metrics) {
        while (start <= end) {
            while (comparator.compare(values.get(start), pivot) < 0) {
                start++;
            }
            while (comparator.compare(values.get(end), pivot) > 0) {
                end--;
            }
            if (start <= end) {
                swap(values, start, end, snapshoter, metrics);
                start++;
                end--;
            }
        }
        return start;
    }
}
