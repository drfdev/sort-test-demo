package def.drf.sort.demo.sort.others;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class PancakeSort extends AbstractSorter<Integer> {
    public PancakeSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public PancakeSort(@NotNull Comparator<Integer> comparator,
                       @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public PancakeSort(@NotNull Comparator<Integer> comparator,
                       @Nullable Snapshoter<Integer> snapshoter,
                       @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Pancake_sorting */
        pancakeSort(values, values.size());
    }

    private void pancakeSort(List<Integer> values, int n) {
        for (int currSize = n; currSize > 1; --currSize) {
            int mi = findMax(values, currSize);

            if (mi != currSize - 1) {
                flip(values, mi);
                flip(values, currSize - 1);
            }
        }
    }

    private void flip(List<Integer> values, int i) {
        int start = 0;
        while (start < i) {
            swap(values, start, i);
            start++;
            i--;
        }
    }

    private int findMax(List<Integer> values, int n) {
        int mi, i;
        for (mi = 0, i = 0; i < n; ++i) {
            if (comparator.compare(values.get(i), values.get(mi)) > 0) {
                mi = i;
            }
        }
        return mi;
    }
}
