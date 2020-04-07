package def.drf.sort.demo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Sorter<T> {
    void sort(@NotNull List<T> values);
}
