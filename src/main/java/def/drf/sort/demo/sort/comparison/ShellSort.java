package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class ShellSort<T> extends AbstractSorter<T> {
    public ShellSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public ShellSort(@NotNull Comparator<T> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public ShellSort(@NotNull Comparator<T> comparator,
                     @Nullable Snapshoter<T> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Shellsort */
        int n = values.size();

        for (int gap = n/2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                T temp = values.get(i);

                int j;
                for (j = i;
                     j >= gap && comparator.compare(values.get(j - gap), temp) > 0;
                     j -= gap) {
//                    values.set(j, values.get(j - gap));
                    setByIndex(values, j, values.get(j - gap), j - gap);
                }

//                values.set(j, temp);
                setByIndex(values, j, temp, i);
            }
        }
    }
}
