package def.drf.sort.demo;

import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractSorter<T> implements Sorter<T> {
    @NotNull
    protected Comparator<T> comparator;
    @Nullable
    protected Snapshoter<T> snapshoter;
    @Nullable
    protected MetricBucket metrics;

    public AbstractSorter(@NotNull Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public AbstractSorter(@NotNull Comparator<T> comparator,
                          @Nullable MetricBucket metrics) {
        this.comparator = comparator;
        this.metrics = metrics;
    }

    public AbstractSorter(@NotNull Comparator<T> comparator,
                          @Nullable Snapshoter<T> snapshoter,
                          @Nullable MetricBucket metrics) {
        this.comparator = comparator;
        this.snapshoter = snapshoter;
        this.metrics = metrics;
    }

    protected void swap(@NotNull List<T> values, int from, int to) {
        if (metrics != null) {
            metrics.metricsData(values.get(from), values.get(to), from, to);
        }
        Collections.swap(values, from, to);

        if (snapshoter != null) {
            snapshoter.snapshot(values);
        }
    }
}
