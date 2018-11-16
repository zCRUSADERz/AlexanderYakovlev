package ru.job4j.loop;

/**
 * Counter.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class Counter {

    /**
     * Adds even numbers from start to finish.
     * @param start - start number.
     * @param finish - finish number.
     * @return - sum of number.
     */
    public int add(int start, int finish) {
        int sum = 0;
        for (int i = start; i <= finish; i++) {
            if ((i % 2) == 0) {
                sum += i;
            }
        }
        return sum;
    }
}
