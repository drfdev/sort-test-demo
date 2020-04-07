package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class QuickSort<T> extends AbstractSorter<T> {
    public enum PartitionScheme {
        LOMUTO, HOARE;
    }

    private final PartitionScheme partitionScheme;

    public QuickSort(PartitionScheme partitionScheme,
                     @NotNull Comparator<T> comparator) {
        super(comparator);
        this.partitionScheme = requireNonNull(partitionScheme);
    }

    public QuickSort(PartitionScheme partitionScheme,
                     @NotNull Comparator<T> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
        this.partitionScheme = requireNonNull(partitionScheme);
    }

    public QuickSort(PartitionScheme partitionScheme,
                     @NotNull Comparator<T> comparator,
                     @Nullable Snapshoter<T> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
        this.partitionScheme = requireNonNull(partitionScheme);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Quicksort */
        if (partitionScheme == PartitionScheme.LOMUTO) {
            quicksortLomuto(values, comparator, 0, values.size() - 1);
            return;
        }

        if (partitionScheme == PartitionScheme.HOARE) {
            quicksortHoare(values, comparator, 0, values.size() - 1);
            return;
        }

        throw new RuntimeException("Wrong PartitionScheme: " + partitionScheme);
    }

    /* Lomuto partition scheme */
    private void quicksortLomuto(List<T> values, Comparator<T> comparator, int lo, int hi) {
        if (lo < hi) {
            int p = partitionLomuto(values, comparator, lo, hi);
            quicksortLomuto(values, comparator, lo, p - 1);
            quicksortLomuto(values, comparator, p + 1, hi);
        }
    }

    private int partitionLomuto(List<T> values, Comparator<T> comparator, int lo, int hi) {
        T pivot = values.get(hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (comparator.compare(values.get(j), pivot) < 0) {
                swap(values, i, j);
                i = i + 1;
            }
        }
        swap(values, i, hi);
        return i;
    }

    /* Hoare partition scheme */
    private void quicksortHoare(List<T> values, Comparator<T> comparator, int lo, int hi) {
        if (lo < hi) {
            int p = partitionHoare(values, comparator, lo, hi);
            quicksortLomuto(values, comparator, lo, p);
            quicksortLomuto(values, comparator, p + 1, hi);
        }
    }

    private int partitionHoare(List<T> values, Comparator<T> comparator, int lo, int hi) {
        T pivot = values.get((hi + lo) / 2);
        int i = lo - 1;
        int j = hi + 1;
        while (true) {
            do {
                i = i + 1;
            } while (comparator.compare(values.get(i), pivot) < 0);
            do {
                j = j - 1;
            } while (comparator.compare(values.get(j), pivot) > 0);
            if (i >= j) {
                return j;
            }
            swap(values, i, j);
        }
    }
}
