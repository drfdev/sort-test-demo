package def.drf.sort.demo.snap;

import def.drf.sort.demo.metric.Metric;

import java.util.List;

public interface Snapshoter<T> {
    List<Snapshot<T>> getSnaps();

    List<Metric<T>> getMetrics();
}
