package ru.job4j;

import java.util.*;
import java.util.stream.Stream;

/**
 * Testing performance collections.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 13.01.2017
 * @version 1.0
 */
public class PerformanceCollections {
    /**
     * Counter for create new unique string.
     */
    private int count = 0;

    /**
     * Test add operation.
     * @param collection - collection for testing.
     * @param amount - amount strings.
     * @return - time in millis.
     */
    public long add(Collection<String> collection, int amount) {
        String[] strings = this.getStrings(amount);
        long start = System.nanoTime();
        collection.addAll(Arrays.asList(strings));
        return System.nanoTime() - start;
    }

    /**
     * Testing delete operation.
     * @param collection - collection for testing.
     * @param amount - amount strings.
     * @return - time in millis.
     */
    public long delete(Collection<String> collection, int amount) {
        final List<String> strings = new ArrayList<>();
        collection.stream()
                .limit(amount)
                .forEach(strings::add);
        Collections.shuffle(strings);
        long start = System.nanoTime();
        strings.forEach(collection::remove);
        return System.nanoTime() - start;
    }

    /**
     * Get array of strings.
     * @param amount - amount strings.
     * @return - array of strings.
     */
    private String[] getStrings(int amount) {
        String[] strings = new String[amount];
        Stream
                .iterate(0, n -> n + 1)
                .limit(amount)
                .forEach(n -> strings[n] = getString());
        for (int i = 0; i < amount; i++) {
            strings[i] = getString();
        }
        return strings;
    }

    /**
     * Get unique string.
     * @return - unique string.
     */
    private String getString() {
        double d = (this.count++) + Math.random();
        return String.valueOf(d);
    }
}
