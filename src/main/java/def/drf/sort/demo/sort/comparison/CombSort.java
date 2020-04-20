package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class CombSort<T> extends AbstractSorter<T> {
    public CombSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public CombSort(@NotNull Comparator<T> comparator,
                    @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public CombSort(@NotNull Comparator<T> comparator,
                    @Nullable Snapshoter<T> snapshoter,
                    @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Comb_sort */

        int n = values.size();
        int gap = n;

        boolean swapped = true;

        while (gap != 1 || swapped) {
            gap = getNextGap(gap);
            swapped = false;

            for (int i = 0; i < n - gap; i++) {
                if (comparator.compare(values.get(i), values.get(i + gap)) > 0) {
                    swap(values, i, i + gap);
                    swapped = true;
                }
            }
        }
    }

    private int getNextGap(int gap) {
        gap = (gap * 10) / 13;
        if (gap < 1) {
            return 1;
        }
        return gap;
    }
}
