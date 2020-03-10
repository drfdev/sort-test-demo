package def.drf.sort.demo.metric;

public final class IterationCountMetric implements Metric<Integer> {
    private int counter;

    public IterationCountMetric() {
        this.counter = 0;
    }

    @Override
    public Integer getValue() {
        return counter;
    }

    @Override
    public <T> void calculateMetric(T from, T to, int fromIndex, int toIndex) {
        counter ++;
    }
}
