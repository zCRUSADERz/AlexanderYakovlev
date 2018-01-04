package ru.job4j.array;

/**
 * Turn array.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class Turn {

    /**
     * Invert array.
     * @param array - array to be inverted.
     * @return inverted array.
     */
    public int[] back(int[] array) {
        int length = array.length;
        for (int i = 0; i < length / 2; i++) {
            int tmp = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = tmp;
        }
        return array;
    }
}
