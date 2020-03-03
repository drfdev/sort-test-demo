package def.drf.sort.demo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;

public interface Sorter {
    <T> void sort(@NotNull Collection<T> values, @Nullable Comparator<T> comparator, @Nullable Snapshoter<T> snapshoter);

    default <T> void sort(@NotNull Collection<T> values, @Nullable Comparator<T> comparator) {
        sort(values, comparator, null);
    }

    default <T> void sort(@NotNull Collection<T> values) {
        sort(values, null, null);
    }
}
