package def.drf.sort.demo;

import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public interface Sorter {
    <T> void sort(@NotNull List<T> values, @Nullable Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter);

    default <T> void sort(@NotNull List<T> values, @Nullable Comparator<T> comparator) {
        sort(values, comparator, null);
    }

    default <T> void sort(@NotNull List<T> values) {
        sort(values, null, null);
    }
}
