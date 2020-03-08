package def.drf.sort.demo.metric;

public interface Metric<V> {
    V getValue();
    <T> void calculateMetric(T from, T to, int fromIndex, int toIndex);
}
