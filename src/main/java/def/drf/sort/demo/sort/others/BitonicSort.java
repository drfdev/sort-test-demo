package def.drf.sort.demo.sort.others;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class BitonicSort extends AbstractSorter<Integer> {
    public BitonicSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public BitonicSort(@NotNull Comparator<Integer> comparator,
                       @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public BitonicSort(@NotNull Comparator<Integer> comparator,
                       @Nullable Snapshoter<Integer> snapshoter,
                       @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Bitonic_sorter */
        // size = 2 ^ N

        bitonicSort(values, 0, values.size(), 1);
    }

    private void bitonicSort(List<Integer> values, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;

            bitonicSort(values, low, k, 1);
            bitonicSort(values, low + k, k, 0);
            bitonicMerge(values, low, cnt, dir);
        }
    }

    private void bitonicMerge(List<Integer> values, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++) {
                compAndSwap(values, i, i + k, dir);
            }
            bitonicMerge(values, low, k, dir);
            bitonicMerge(values, low + k, k, dir);
        }
    }

    private void compAndSwap(List<Integer> values, int i, int j, int dir) {
        if ( (comparator.compare(values.get(i), values.get(j)) > 0 && dir == 1) ||
                (comparator.compare(values.get(i), values.get(j)) < 0 && dir == 0)) {
            swap(values, i, j);
        }
    }
}
