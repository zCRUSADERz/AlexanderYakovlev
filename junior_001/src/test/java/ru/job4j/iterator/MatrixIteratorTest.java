package ru.job4j.iterator;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Matrix iterator test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class MatrixIteratorTest {
    private Iterator<Integer> it;

    @Test
    public void whenArrayHaveAnyElementThenHasNextReturnTrue() {
        it = new MatrixIterator<>(new Integer[][]{{}, {1}});
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }

    @Test
    public void whenArrayHaveOneElementsThenHasNextAfterNextReturnFalse() {
        it = new MatrixIterator<>(new Integer[][]{{}, {1}, {}});
        it.next();
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenArrayDontHaveAnyElementsThenNextThrowNoSuchElementException() {
        it = new MatrixIterator<>(new Integer[0][0]);
        it.next();
    }

    @Test
    public void whenRemoveElementThenNextReturnNextElement() {
        it = new MatrixIterator<>(new Integer[][]{{1, 2, 3}, {}});
        it.next();
        it.next();
        it.remove();
        Integer result = it.next();
        Integer expected = 3;
        assertThat(result, is(expected));
    }

    @Test
    public void whenRemoveLastElementInSubArrayThenNextReturnNextElement() {
        it = new MatrixIterator<>(new Integer[][]{{2}, {3}});
        it.next();
        it.remove();
        Integer result = it.next();
        Integer expected = 3;
        assertThat(result, is(expected));
    }

    @Test (expected = IllegalStateException.class)
    public void whenNextNotCalledAndRemoveElementThenThrowsIllegalStateException() {
        it = new MatrixIterator<>(new Integer[][]{{1, 2, 3}, {}});
        it.remove();
    }

    @Test (expected = IllegalStateException.class)
    public void whenNextAndTwoCallsRemoveThenThrowsIllegalStateException() {
        it = new MatrixIterator<>(new Integer[][]{{1, 2}, {1}});
        it.next();
        it.next();
        it.remove();
        it.remove();
    }
}