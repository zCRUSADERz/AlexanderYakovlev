package ru.job4j.max;

/**
 * Max.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.12.2017
 * @version 1.0
 */
public class Max {

    /**
     * Max number.
     * @param first - first number
     * @param second - second number
     * @return max
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }

    public int max(int first, int second, int third) {
        int maximumOfTwo = max(first, second);
        return max(maximumOfTwo, third);
    }
}
