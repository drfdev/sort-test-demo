package def.drf.sort.demo.sort.noncomparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class RadixSort extends AbstractSorter<Integer> {
    public RadixSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public RadixSort(@NotNull Comparator<Integer> comparator,
                     @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public RadixSort(@NotNull Comparator<Integer> comparator,
                     @Nullable Snapshoter<Integer> snapshoter,
                     @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Radix_sort */
        int m = getMax(values);

        for (int exp = 1; m / exp > 0; exp *= 10) {
            countSort(values, exp);
        }
    }

    private int getMax(List<Integer> values) {
        return values.stream()
                .max(comparator)
                .get();
    }

    private void countSort(List<Integer> values, int exp) {
        int n = values.size();

        int output[] = new int[n];
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        for (i = 0; i < n; i++) {
            count[(values.get(i) / exp) % 10]++;
        }

        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (i = n - 1; i >= 0; i--) {
            output[count[(values.get(i) / exp) % 10] - 1] = values.get(i);
            count[(values.get(i) / exp) % 10]--;
        }

        for (i = 0; i < n; i++) {
            setByIndex(values, i, output[i], i);
        }
    }
}
