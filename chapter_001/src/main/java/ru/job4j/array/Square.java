package ru.job4j.array;

/**
 * Square.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class Square {

    /**
     * Fills the array with numbers squared.
     * @param bond - array size.
     * @return - array of numbers squared.
     */
    public int[] calculate(int bond) {
        int[] result = new int[bond];
        for (int i = 1; i <= bond; i++) {
            result[i - 1] = (int) Math.pow(i, 2);
        }
        return result;
    }
}
