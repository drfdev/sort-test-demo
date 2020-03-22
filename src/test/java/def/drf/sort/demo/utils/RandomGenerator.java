package def.drf.sort.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomGenerator {
    private RandomGenerator() {
    }

    public static List<Integer> newListWithRandomValues(final int size) {
        List<Integer> values = new ArrayList<>(size);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int min = random.nextInt(0, size);
        int max = random.nextInt(min + 1, size * 10);

        for (int i = 0; i < size; i++) {
            int newValue = random.nextInt(min, max);
            values.add(newValue);
        }

        return values;
    }
}
