package ru.job4j.array;

/**
 * Matrix.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class Matrix {

    /**
     * Multiplication table.
     * @param size - size array.
     * @return - array with multiplication table.
     */
    public int[][] multiple(int size) {
        int[][] array = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = i * j;
            }
        }
        return array;
    }
}
