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
        // TODO
    }
}
