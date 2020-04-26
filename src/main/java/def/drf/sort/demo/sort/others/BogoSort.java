package def.drf.sort.demo.sort.others;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class BogoSort extends AbstractSorter<Integer> {
    public BogoSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public BogoSort(@NotNull Comparator<Integer> comparator,
                    @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public BogoSort(@NotNull Comparator<Integer> comparator,
                    @Nullable Snapshoter<Integer> snapshoter,
                    @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Bogosort */
        while (!isSorted(values)) {
            shuffle(values);
        }
    }

    private void shuffle(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            bogoSwap(values, i, (int) (Math.random() * i));
        }
    }

    private void bogoSwap(List<Integer> values, int i, int j) {
        swap(values, i, j);
    }

    private boolean isSorted(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (comparator.compare(values.get(i), values.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}
