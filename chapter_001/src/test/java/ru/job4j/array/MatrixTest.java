package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Matrix test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class MatrixTest {

    @Test
    public void whenSizeArrayFourThenArray() {
        Matrix matrix = new Matrix();
        int[][] result = matrix.multiple(4);
        int[][] expected = {{0, 0, 0, 0}, {0, 1, 2, 3}, {0, 2, 4, 6}, {0, 3, 6, 9}};
        assertThat(result, is(expected));
    }
}
