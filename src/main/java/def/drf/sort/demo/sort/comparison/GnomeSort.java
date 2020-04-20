package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class GnomeSort<T> extends AbstractSorter<T> {
    public GnomeSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public GnomeSort(@NotNull Comparator<T> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public GnomeSort(@NotNull Comparator<T> comparator,
                     @Nullable Snapshoter<T> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Gnome_sort */
        int n = values.size();
        int index = 0;

        while (index < n) {
            if (index == 0) {
                index++;
            }
            if (comparator.compare(values.get(index), values.get(index - 1))  >= 0) {
                index++;
            } else {
                swap(values, index, index - 1);
                index--;
            }
        }
    }
}
