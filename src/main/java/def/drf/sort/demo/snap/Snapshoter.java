package def.drf.sort.demo.snap;

import java.util.List;

public interface Snapshoter<T> {
    List<Snapshot<T>> getSnaps();
}
