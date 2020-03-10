package def.drf.sort.demo.metric.simple;

import def.drf.sort.demo.metric.Metric;
import def.drf.sort.demo.metric.MetricBucket;

import java.util.ArrayList;
import java.util.List;

public final class SimpleMetricBucket implements MetricBucket {
    private final List<Metric<?>> metrics;

    private SimpleMetricBucket(List<Metric<?>> metrics) {
        this.metrics = metrics;
    }

    @Override
    public List<Metric<?>> getMetrics() {
        return metrics;
    }

    @Override
    public <T> void metricsData(T from, T to, int fromIndex, int toIndex) {
        for (Metric<?> metric : metrics) {
            metric.calculateMetric(from, to, fromIndex, toIndex);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Metric<?>> metrics;

        private Builder() {
            this.metrics = new ArrayList<>();
        }

        public Builder addMetric(Metric<?> metric) {
            this.metrics.add(metric);
            return this;
        }

        public SimpleMetricBucket build() {
            return new SimpleMetricBucket(this.metrics);
        }
    }
}
