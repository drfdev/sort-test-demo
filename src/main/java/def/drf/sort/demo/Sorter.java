package def.drf.sort.demo;

import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface Sorter {
    <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter, @Nullable MetricBucket metrics);

    default <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator) {
        sort(values, comparator, null, null);
    }

    default <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter) {
        sort(values, comparator, snapshoter, null);
    }

    default <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable MetricBucket metrics) {
        sort(values, comparator, null, metrics);
    }

    default <T> void swap(@NotNull List<T> values, int from, int to, @Nullable Snapshoter<T> snapshoter, @Nullable MetricBucket metrics) {
        if (metrics != null) {
            metrics.metricsData(values.get(from), values.get(to), from, to);
        }
        Collections.swap(values, from, to);

        if (snapshoter != null) {
            snapshoter.snapshot(values);
        }
    }
}
