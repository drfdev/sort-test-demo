package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class BubbleSort<T> extends AbstractSorter<T> {
    public BubbleSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public BubbleSort(@NotNull Comparator<T> comparator,
                      @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public BubbleSort(@NotNull Comparator<T> comparator,
                      @Nullable Snapshoter<T> snapshoter,
                      @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://www.geeksforgeeks.org/bubble-sort/ */
        int n = values.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(values.get(j), values.get(j + 1)) > 0) {
                    swap(values, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }
}
