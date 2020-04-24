package def.drf.sort.demo.sort.noncomparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class BucketSort<T extends Number> extends AbstractSorter<T> {
    private static final int BUCKET_COUNT = 10;

    public BucketSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public BucketSort(@NotNull Comparator<T> comparator,
                      @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public BucketSort(@NotNull Comparator<T> comparator,
                      @Nullable Snapshoter<T> snapshoter,
                      @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Bucket_sort */
        int bucketCount = BUCKET_COUNT;
        List<T>[] buckets = new List[bucketCount];
        T m = maxValue(values);

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<T>();
        }

        for (T val : values) {
            int index = (int) Math.floor(((bucketCount - 1) * val.doubleValue()) / m.doubleValue());

            buckets[index].add(val);
        }

        for (List<T> bucket : buckets) {
            insertionSort(bucket);
        }

        int k = 0;
        for (List<T> bucket : buckets) {
            for (T bucketValue : bucket) {
                values.set(k, bucketValue);
                k ++;
            }
        }
    }

    private T maxValue(List<T> values) {
        return values.stream()
                .max(comparator)
                .get();
    }

    private void insertionSort(List<T> bucket) {
        for (int i = 0; i < bucket.size(); i++) {
            T up = bucket.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(bucket.get(j), up) > 0) {
                setByIndex(bucket, j + 1, bucket.get(j), j);
                j -= 1;
            }
            setByIndex(bucket, j + 1, up, i);
        }
    }
}
