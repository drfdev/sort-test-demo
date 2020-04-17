package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class CycleSort<T> extends AbstractSorter<T> {
    public CycleSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public CycleSort(@NotNull Comparator<T> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public CycleSort(@NotNull Comparator<T> comparator,
                     @Nullable Snapshoter<T> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Cycle_sort */
        int n = values.size();

        for (int cycle_start = 0; cycle_start <= n - 2; cycle_start++) {
            T item = values.get(cycle_start);

            int pos = cycle_start;
            for (int i = cycle_start + 1; i < n; i++)
                if (comparator.compare(values.get(i), item) < 0) {
                    pos++;
                }

            if (pos == cycle_start) {
                continue;
            }

            while (comparator.compare(item, values.get(pos)) == 0) {
                pos += 1;
            }

            if (pos != cycle_start) {
                T temp = item;
                item = values.get(pos);
                setByIndex(values, pos, temp, pos);
            }

            while (pos != cycle_start) {
                pos = cycle_start;

                for (int i = cycle_start + 1; i < n; i++)
                    if (comparator.compare(values.get(i), item) < 0) {
                        pos += 1;
                    }

                while (comparator.compare(item, values.get(pos)) == 0) {
                    pos += 1;
                }

                if (item != values.get(pos)) {
                    T temp = item;
                    item = values.get(pos);
                    setByIndex(values, pos, temp, pos);
                }
            }
        }
    }
}
