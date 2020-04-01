package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.Sorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MergeSort implements Sorter {
    public enum Implementation {
        TOP_DOWN, BOTTOM_UP
    }

    private final Implementation implementation;

    public MergeSort(Implementation implementation) {
        this.implementation = implementation;
    }

    @Override
    public <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter, @Nullable MetricBucket metrics) {
        /* https://en.wikipedia.org/wiki/Merge_sort */
        if (implementation == Implementation.TOP_DOWN) {
            final int n = values.size();
            List<T> copy = new ArrayList<>(values);
            topDownSplitMerge(copy, 0, n, values, comparator, snapshoter, metrics);
            return;
        }
        if (implementation == Implementation.BOTTOM_UP) {
            // TODO
            return;
        }

        throw new RuntimeException("Wrong implementation: " + implementation);
    }

    private <T> void topDownSplitMerge(List<T> b, int begin, int end, List<T> a,
                                       Comparator<T> comparator, Snapshoter<T> snapshoter, MetricBucket metrics) {
        // Top-down implementation
        if (end - begin < 2) {
            return;
        }
        int middle = (end + begin) / 2;

        topDownSplitMerge(a, begin, middle, b, comparator, snapshoter, metrics);
        topDownSplitMerge(a, middle, end, b, comparator, snapshoter, metrics);

        topDownMerge(b, begin, middle, end, a, comparator, snapshoter, metrics);
    }

    private <T> void topDownMerge(List<T> a, int begin, int middle, int end, List<T> b,
                                  Comparator<T> comparator, Snapshoter<T> snapshoter, MetricBucket metrics) {
        int i = begin;
        int j = middle;

        for (int k = begin; k < end; k++) {
            if (i < middle && (j >= end || comparator.compare(a.get(i), a.get(j)) <= 0)) {
//                b.set(k, a.get(i));
                update(a, b, i, k, snapshoter, metrics);
                i = i + 1;
            } else {
//                b.set(k, a.get(j));
                update(a, b, j, k, snapshoter, metrics);
                j = j + 1;
            }
        }
    }

    private <T> void update(List<T> fromLst, List<T> toList, int from, int to, Snapshoter<T> snapshoter, MetricBucket metrics) {
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
}
