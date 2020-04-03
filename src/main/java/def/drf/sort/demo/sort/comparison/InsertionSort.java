package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.Sorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class InsertionSort implements Sorter {
    @Override
    public <T> void sort(@NotNull List<T> values, @NotNull Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter, @Nullable MetricBucket metrics) {
        /* https://en.wikipedia.org/wiki/Introsort */
        // TODO
    }
}
