package def.drf.sort.demo.sort.noncomparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class SpreadSort extends AbstractSorter<Integer> {
    public SpreadSort(@NotNull Comparator<Integer> comparator) {
        super(comparator);
    }

    public SpreadSort(@NotNull Comparator<Integer> comparator,
                      @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public SpreadSort(@NotNull Comparator<Integer> comparator,
                      @Nullable Snapshoter<Integer> snapshoter,
                      @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    private static final int MAX_SPLITS = 11;
    private static final int LOG_MEAN_BIN_SIZE = 2;
    private static final int LOG_MIN_SPLIT_COUNT = 9;
    private static final int LOG_CONST = 4;


    private static class Bin {
        int position;
        int count;
    }

    private static class SliceIntArray {
        public int[] array;
        public int index;
        public int length;

        public SliceIntArray(int[] array, int idx) {
            Objects.requireNonNull(array, "The array cannot be null");
            if (idx < 0) {
                throw new IllegalArgumentException("The index cannot be negative");
            }

            this.array = array;
            this.length = array.length;
            this.index = idx;
        }
    }

    @Override
    public void sort(@NotNull List<Integer> values) {
        /* https://en.wikipedia.org/wiki/Spreadsort */
        int n = values.size();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = values.get(i);
        }
        sort(array, 0, n);

        for (int i = 0; i < n; i++) {
            values.set(i, array[i]);
        }
    }

    private void sort(int[] array, int idx, int count) {
        if (count < 2) {
            return;
        }

        final int[] minMaxCount = new int[3];
        SliceIntArray sia = new SliceIntArray(array, idx);
        final Bin[] bins = spreadSortCore(sia, count, minMaxCount);

        if (bins == null) {
            return;
        }

        final int maxCount = getMaxCount(roughLog2(minMaxCount[1] - minMaxCount[0]), count);
        spreadSortBins(sia, minMaxCount, bins, maxCount);
    }

    private int roughLog2(int x) {
        int shift = 0;
        x = (x + (x >> 31)) ^ (x >> 31);

        while ((x >> shift) != 0) {
            shift++;
        }

        return shift;
    }

    private int getMaxCount(int logRange, int count) {
        int logSize = roughLog2(count);

        if (logSize > MAX_SPLITS) {
            logSize = MAX_SPLITS;
        }

        int relativeWidth = (LOG_CONST * logRange) / logSize;

        if (relativeWidth >= 4) {
            relativeWidth = 3;
        }

        final int shift = Math.max(relativeWidth, LOG_MEAN_BIN_SIZE + LOG_MIN_SPLIT_COUNT);

        return 1 << shift;
    }

    private void findExtremes(SliceIntArray sia, int count, int[] minMax) {
        final int[] input = sia.array;
        final int end = sia.index + count;
        int min = input[sia.index];
        int max = min;

        for (int i = sia.index; i < end; i++) {
            final int val = input[i];

            if (val > max) {
                max = val;
            } else if (val < min) {
                min = val;
            }
        }

        minMax[0] = min;
        minMax[1] = max;
    }

    private Bin[] spreadSortCore(SliceIntArray sia, int count, int[] minMaxCount) {
        findExtremes(sia, count, minMaxCount);
        final int max = minMaxCount[1];
        final int min = minMaxCount[0];

        if (max == min) {
            return null;
        }

        final int logRange = roughLog2(max - min);
        int logDivisor = logRange - roughLog2(count) + LOG_MEAN_BIN_SIZE;

        if (logDivisor < 0) {
            logDivisor = 0;
        }

        if (logRange - logDivisor > MAX_SPLITS) {
            logDivisor = logRange - MAX_SPLITS;
        }

        final int divMin = min >> logDivisor;
        final int divMax = max >> logDivisor;
        final int binCount = divMax - divMin + 1;

        final Bin[] bins = new Bin[binCount];

        for (int i = 0; i < binCount; i++) {
            bins[i] = new Bin();
        }

        final int[] array = sia.array;
        final int count8 = count & -8;
        final int end8 = sia.index + count8;

        for (int i = sia.index; i < end8; i += 8) {
            bins[(array[i] >> logDivisor) - divMin].count++;
            bins[(array[i + 1] >> logDivisor) - divMin].count++;
            bins[(array[i + 2] >> logDivisor) - divMin].count++;
            bins[(array[i + 3] >> logDivisor) - divMin].count++;
            bins[(array[i + 4] >> logDivisor) - divMin].count++;
            bins[(array[i + 5] >> logDivisor) - divMin].count++;
            bins[(array[i + 6] >> logDivisor) - divMin].count++;
            bins[(array[i + 7] >> logDivisor) - divMin].count++;
        }

        for (int i = count8; i < count; i++) {
            bins[(array[sia.index + i] >> logDivisor) - divMin].count++;
        }

        bins[0].position = sia.index;

        for (int i = 0; i < binCount - 1; i++) {
            bins[i + 1].position = bins[i].position + bins[i].count;
            bins[i].count = bins[i].position - sia.index;
        }

        bins[binCount - 1].count = bins[binCount - 1].position - sia.index;

        for (int i = 0; i < count; ++i) {
            Bin currBin;
            final int idx = sia.index + i;

            for (currBin = bins[(array[idx] >> logDivisor) - divMin]; currBin.count > i; ) {
                final int tmp = array[currBin.position];
                array[currBin.position] = array[idx];
                array[idx] = tmp;
                currBin.position++;
                currBin = bins[(array[idx] >> logDivisor) - divMin];
            }

            if (currBin.position == idx) {
                currBin.position++;
            }
        }

//        minMaxCount[0] = min;
//        minMaxCount[1] = max;
        minMaxCount[2] = binCount;

        if (logDivisor == 0) {
            return null;
        }

        return bins;
    }

    private void spreadSortBins(SliceIntArray sia, int[] minMaxCount, Bin[] bins, int maxCount) {
        final int binCount = minMaxCount[2];

        for (int i = 0; i < binCount; i++) {
            final int n = (bins[i].position - sia.index) - bins[i].count;

            if (n < 2) {
                continue;
            }

            if (n < maxCount) {
                Arrays.sort(sia.array, sia.index + bins[i].count, bins[i].position);
            } else {
                sort(sia.array, sia.index + bins[i].count, n);
            }
        }
    }

}
