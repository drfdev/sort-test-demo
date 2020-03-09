package def.drf.sort.demo.metric;

import java.util.List;

public interface MetricBucket {
    List<Metric<?>> getMetrics();
    <T> void metricsData(T from, T to, int fromIndex, int toIndex);
}
