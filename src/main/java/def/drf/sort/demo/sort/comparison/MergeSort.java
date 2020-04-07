package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;

public final class MergeSort<T> extends AbstractSorter<T> {
    public enum Implementation {
        TOP_DOWN, BOTTOM_UP
    }

    private final Implementation implementation;

    public MergeSort(Implementation implementation,
                     @NotNull Comparator<T> comparator) {
        super(comparator);
        this.implementation = requireNonNull(implementation);
    }

    public MergeSort(Implementation implementation,
                     @NotNull Comparator<T> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
        this.implementation = requireNonNull(implementation);
    }

    public MergeSort(Implementation implementation,
                     @NotNull Comparator<T> comparator,
                     @Nullable Snapshoter<T> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
        this.implementation = requireNonNull(implementation);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Merge_sort */
        if (implementation == Implementation.TOP_DOWN) {
            final int n = values.size();
            List<T> copy = new ArrayList<>(values);
            topDownSplitMerge(copy, 0, n, values);
            return;
        }
        if (implementation == Implementation.BOTTOM_UP) {
            final int n = values.size();
            List<T> copy = new ArrayList<>(values);
            bottomUpMergeSort(copy, values, n);
            return;
        }

        throw new RuntimeException("Wrong implementation: " + implementation);
    }

    private void topDownSplitMerge(List<T> b, int begin, int end, List<T> a) {
        // Top-down implementation
        if (end - begin < 2) {
            return;
        }
        int middle = (end + begin) / 2;

        topDownSplitMerge(a, begin, middle, b);
        topDownSplitMerge(a, middle, end, b);

        topDownMerge(b, begin, middle, end, a);
    }

    private void topDownMerge(List<T> a, int begin, int middle, int end, List<T> b) {
        int i = begin;
        int j = middle;

        for (int k = begin; k < end; k++) {
            if (i < middle && (j >= end || comparator.compare(a.get(i), a.get(j)) <= 0)) {
//                b.set(k, a.get(i));
                update(a, b, i, k);
                i = i + 1;
            } else {
//                b.set(k, a.get(j));
                update(a, b, j, k);
                j = j + 1;
            }
        }
    }

    private void update(List<T> fromLst, List<T> toList, int from, int to) {
        if (metrics != null) {
            T fromValue = fromLst.get(from);
            T toValue = toList.get(to);

            metrics.metricsData(fromValue, toValue, from, to);
        }

        toList.set(to, fromLst.get(from));

        if (snapshoter != null) {
            snapshoter.snapshot(toList);
        }
    }

    private void bottomUpMergeSort(List<T> a, List<T> b, int n) {
        for (int width = 1; width < n; width = 2 * width) {
            for (int i = 0; i < n; i = i + 2 * width) {
                bottomUpMerge(a, i, min(i + width, n), min(i + 2 * width, n), b);
            }
            copyArray(b, a, n);
        }
    }

    private void bottomUpMerge(List<T> a, int left, int right, int end, List<T> b) {
        int i = left;
        int j = right;
        for (int k = left; k < end; k++) {
            if (i < right && (j >= end || comparator.compare(a.get(i), a.get(j)) <= 0)) {
//                b.set(k, a.get(i));
                update(a, b, i, k);
                i = i + 1;
            } else {
//                b.set(k, a.get(j));
                update(a, b, j, k);
                j = j + 1;
            }
        }
    }

    private void copyArray(List<T> b, List<T> a, int n) {
        for (int i = 0; i < n; i++) {
//            a.set(i, b.get(i));
            update(b, a, i, i);
            // x2 iteration count, wtf ?
        }
    }
}
