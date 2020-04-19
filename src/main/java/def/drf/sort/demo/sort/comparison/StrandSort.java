package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class StrandSort<T> extends AbstractSorter<T> {
    private static class StrandSortContext<T> {
        @NotNull List<T> values;
        @NotNull LinkedList<T> origList;
        @NotNull LinkedList<T> solList;
        int k = 0;

        public StrandSortContext(@NotNull List<T> values) {
            this.values = values;
            this.origList = new LinkedList<>(values);
            this.solList = new LinkedList<>();
        }
    }

    public StrandSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public StrandSort(@NotNull Comparator<T> comparator,
                      @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public StrandSort(@NotNull Comparator<T> comparator,
                      @Nullable Snapshoter<T> snapshoter,
                      @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Strand_sort */

        StrandSortContext<T> context = new StrandSortContext<>(values);
        strandSortIterative(context);
        for (int i = 0; i < values.size(); i++) {
            values.set(i, context.solList.get(i));
        }

        // How to calculate metric ??
    }

    private void strandSortIterative(StrandSortContext<T> context) {
        if (context.origList.isEmpty()) {
            return;
        } else {
            LinkedList<T> subList = new LinkedList<>();
            subList.add(context.origList.getFirst());
            context.origList.removeFirst();

            int index = 0;

            for (int j = 0; j < context.origList.size(); j++) {
                if (comparator.compare(context.origList.get(j), subList.get(index)) > 0) {
                    subList.add(context.origList.get(j));
                    context.origList.remove(j);
                    j = j - 1;
                    index = index + 1;
                }
            }

            if (context.k == 0) {
                for (int i = 0; i < subList.size(); i++) {
                    context.solList.add(subList.get(i));
                    context.k = context.k + 1;
//                    saveMetrics(context.solList, subList.get(i), context.solList.size());
                }
            }

            else {
                int subEnd = subList.size() - 1;
                int solStart = 0;

                while (!subList.isEmpty()) {
                    if (comparator.compare(subList.get(subEnd), context.solList.get(solStart)) > 0) {
                        solStart++;
                    } else {
                        context.solList.add(solStart, subList.get(subEnd));
//                        saveMetrics(context.solList, subList.get(subEnd), solStart);
                        subList.remove(subEnd);
                        subEnd--;
                        solStart = 0;
                    }
                }
            }
            strandSortIterative(context);
        }
    }

    private void saveMetrics(List<T> toSpanshot, T value, int index) {
        if (metrics != null) {
            metrics.metricsData(null, value, -1, index);
        }
        if (snapshoter != null) {
            snapshoter.snapshot(toSpanshot);
        }
    }
}
