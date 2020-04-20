package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class CocktailSort<T> extends AbstractSorter<T> {
    public CocktailSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public CocktailSort(@NotNull Comparator<T> comparator,
                        @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public CocktailSort(@NotNull Comparator<T> comparator,
                        @Nullable Snapshoter<T> snapshoter,
                        @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Cocktail_shaker_sort */
        boolean swapped = true;
        int start = 0;
        int end = values.size();

        while (swapped) {
            swapped = false;

            for (int i = start; i < end - 1; ++i) {
                if (comparator.compare(values.get(i), values.get(i + 1)) > 0) {
                    swap(values, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }

            swapped = false;
            end = end - 1;

            for (int i = end - 1; i >= start; i--) {
                if (comparator.compare(values.get(i), values.get(i + 1)) > 0) {
                    swap(values, i, i + 1);
                    swapped = true;
                }
            }

            start = start + 1;
        }
    }
}
