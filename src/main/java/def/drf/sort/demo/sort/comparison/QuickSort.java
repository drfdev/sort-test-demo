package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.Sorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class QuickSort implements Sorter {
    public enum PartitionScheme {
        LOMUTO, HOARE;
    }

    private final PartitionScheme partitionScheme;

    public QuickSort(PartitionScheme partitionScheme) {
        this.partitionScheme = requireNonNull(partitionScheme);
    }

    @Override
    public <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter, @Nullable MetricBucket metrics) {
        /* https://en.wikipedia.org/wiki/Quicksort */
        if (partitionScheme == PartitionScheme.LOMUTO) {
            quicksortLomuto(values, comparator, 0, values.size() - 1, snapshoter, metrics);
            return;
        }

        if (partitionScheme == PartitionScheme.HOARE) {
            quicksortHoare(values, comparator, 0, values.size() - 1, snapshoter, metrics);
            return;
        }

        throw new RuntimeException("Wrong PartitionScheme: " + partitionScheme);
    }

    /* Lomuto partition scheme */
    private <T> void quicksortLomuto(List<T> values, Comparator<T> comparator, int lo, int hi,
                                     Snapshoter<T> snapshoter, MetricBucket metrics) {
        if (lo < hi) {
            int p = partitionLomuto(values, comparator, lo, hi, snapshoter, metrics);
            quicksortLomuto(values, comparator, lo, p - 1, snapshoter, metrics);
            quicksortLomuto(values, comparator, p + 1, hi, snapshoter, metrics);
        }
    }

    private <T> int partitionLomuto(List<T> values, Comparator<T> comparator, int lo, int hi,
                              Snapshoter<T> snapshoter, MetricBucket metrics) {
        T pivot = values.get(hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (comparator.compare(values.get(j), pivot) < 0) {
                swap(values, i, j, snapshoter, metrics);
                i = i + 1;
            }
        }
        swap(values, i, hi, snapshoter, metrics);
        return i;
    }

    /* Hoare partition scheme */
    private <T> void quicksortHoare(List<T> values, Comparator<T> comparator, int lo, int hi,
                                     Snapshoter<T> snapshoter, MetricBucket metrics) {
        if (lo < hi) {
            int p = partitionHoare(values, comparator, lo, hi, snapshoter, metrics);
            quicksortLomuto(values, comparator, lo, p, snapshoter, metrics);
            quicksortLomuto(values, comparator, p + 1, hi, snapshoter, metrics);
        }
    }

    private <T> int partitionHoare(List<T> values, Comparator<T> comparator, int lo, int hi,
                                    Snapshoter<T> snapshoter, MetricBucket metrics) {
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
            swap(values, i, j, snapshoter, metrics);
        }
    }
}
