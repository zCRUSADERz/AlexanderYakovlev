package ru.job4j;

import java.util.Collection;

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
        String[] strings = getStrings(amount);
        long start = System.nanoTime();
        for (String string : strings) {
            collection.add(string);
        }
        return System.nanoTime() - start;
    }

    /**
     * Testing delete operation.
     * @param collection - collection for testing.
     * @param amount - amount strings.
     * @return - time in millis.
     */
    public long delete(Collection<String> collection, int amount) {
        String[] strings = new String[amount];
        int i = 0;
        for (String string : collection) {
            if (i == strings.length) {
                break;
            }
            strings[i++] = string;
        }
        long start = System.nanoTime();
        for (String string : strings) {
            collection.remove(string);
        }
        return System.nanoTime() - start;
    }

    /**
     * Get array of strings.
     * @param amount - amount strings.
     * @return - array of strings.
     */
    private String[] getStrings(int amount) {
        String[] strings = new String[amount];
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
        double d = (count++) + Math.random();
        return String.valueOf(d);
    }
}
