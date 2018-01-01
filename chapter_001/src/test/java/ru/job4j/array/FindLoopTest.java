package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * FindLoop test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class FindLoopTest {

    @Test
    public void whenElementIsInArrayThenIndexElement() {
        FindLoop findLoop = new FindLoop();
        int[] data = {0, 5, 8, 12, 1};
        int el = 8;
        int result = findLoop.indexOf(data, el);
        int expected = 2;
        assertThat(result, is(expected));
    }

    @Test
    public void whenElementIsNotInTheArrayThenIndexMinusOne() {
        FindLoop findLoop = new FindLoop();
        int[] data = {0, 5, 8, 12, 1};
        int el = 3;
        int result = findLoop.indexOf(data, el);
        int expected = -1;
        assertThat(result, is(expected));
    }
}
