package def.drf.sort.demo.sort.others;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class StoogeSort extends AbstractSorter<Integer> {
    public StoogeSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public StoogeSort(@NotNull Comparator<Integer> comparator,
                      @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public StoogeSort(@NotNull Comparator<Integer> comparator,
                      @Nullable Snapshoter<Integer> snapshoter,
                      @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Stooge_sort */
        stoogesort(values, 0, values.size() - 1);
    }

    private void stoogesort(List<Integer> values, int l, int h) {
        if (l >= h) {
            return;
        }

        if (comparator.compare(values.get(l), values.get(h)) > 0) {
            swap(values, l, h);
        }

        if (h - l + 1 > 2) {
            int t = (h - l + 1) / 3;

            stoogesort(values, l, h - t);
            stoogesort(values, l + t, h);
            stoogesort(values, l, h - t);
        }
    }
}
