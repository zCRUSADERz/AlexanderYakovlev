package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Square test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class SquareTest {

    @Test
    public void whenBondFiveThenArray() {
        Square square = new Square();
        int[] result = square.calculate(5);
        int[] expected = {1, 4, 9, 16, 25};
        assertThat(result, is(expected));
    }
}
