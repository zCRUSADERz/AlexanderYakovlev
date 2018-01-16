package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Cell test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.01.2017
 * @version 1.0
 */
public class CellTest {

    @Test
    public void whenOneCellNotEqualsSecond() {
        Cell one = new Cell(0, 0);
        Cell second = new Cell(1, 1);
        boolean result = one.equals(second);
        assertThat(result, is(false));
    }

    @Test
    public void whenOneCellNotEqualsObject() {
        Cell one = new Cell(0, 0);
        Object second = new Object();
        boolean result = one.equals(second);
        assertThat(result, is(false));
    }

    @Test
    public void whenOneCellNotEqualsNull() {
        Cell one = new Cell(0, 0);
        Object second = null;
        boolean result = one.equals(second);
        assertThat(result, is(false));
    }
}