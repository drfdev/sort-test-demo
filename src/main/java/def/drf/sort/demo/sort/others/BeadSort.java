package def.drf.sort.demo.sort.others;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class BeadSort extends AbstractSorter<Integer> {
    public BeadSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public BeadSort(@NotNull Comparator<Integer> comparator,
                    @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public BeadSort(@NotNull Comparator<Integer> comparator,
                    @Nullable Snapshoter<Integer> snapshoter,
                    @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Bead_sort */
        int n = values.size();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = values.get(i);
        }
        int[] result = breadSort(array);

        for (int i = 0; i < n; i++) {
            values.set(i, result[i]);
        }
    }

    private int[] breadSort(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        char[][] grid = new char[arr.length][max];
        int[] levelCount = new int[max];
        for (int i = 0; i < max; i++) {
            levelCount[i] = 0;
            for (int j = 0; j < arr.length; j++) {
                grid[j][i] = '_';
            }
        }

        for (int value : arr) {
            int num = value;
            for (int j = 0; num > 0; j++) {
                grid[levelCount[j]++][j] = '*';
                num--;
            }
        }

        int[] sorted = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int putt = 0;
            for (int j = 0; j < max && grid[arr.length - 1 - i][j] == '*'; j++)
                putt++;
            sorted[i] = putt;
        }

        return sorted;
    }
}
