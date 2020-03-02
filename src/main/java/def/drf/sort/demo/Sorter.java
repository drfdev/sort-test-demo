package def.drf.sort.demo;

import java.util.Collection;

public interface Sorter {
    <T> void sort(Collection<T> values);

    void snapshot(Snapshoter snapshoter);
}
