package def.drf.sort.demo;

import java.util.List;

public interface Snapshoter<T> {
    List<Snapshot<T>> getSnaps();
}
