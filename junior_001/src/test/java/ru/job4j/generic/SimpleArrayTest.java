package ru.job4j.generic;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Simple array test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class SimpleArrayTest {

    @Test (expected = IllegalArgumentException.class)
    public void whenNewSimpleArrayWithCapacityLessThanZeroThenException() {
        SimpleArray<Integer> arr = new SimpleArray<>(-1);
    }

    @Test
    public void whenNewSimpleArrayWithCapacityZeroThenIteratorHasNotNextElement() {
        SimpleArray<Integer> arr = new SimpleArray<>(0);
        Iterator<Integer> it = arr.iterator();
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }

    @Test
    public void whenAddElementThenGetReturnThisElement() {
        SimpleArray<Integer> arr = new SimpleArray<>(5);
        arr.add(1);
        Integer result = arr.get(0);
        assertThat(result, is(1));
    }

    @Test
    public void whenSetElementThenGetReturnChangeElement() {
        SimpleArray<Integer> arr = new SimpleArray<>(5);
        arr.add(1);
        arr.set(0, 2);
        Integer result = arr.get(0);
        assertThat(result, is(2));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenDeleteWithWrongIndexThenException() {
        SimpleArray<Integer> arr = new SimpleArray<>(5);
        arr.delete(1);
    }

    @Test
    public void whenRemoveInIterator() {
        SimpleArray<Integer> arr = new SimpleArray<>(5);
        arr.add(1);
        arr.add(2);
        Iterator<Integer> it = arr.iterator();
        it.next();
        it.remove();
        Integer result = it.next();
        assertThat(result, is(2));
        assertThat(it.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenNextNonExistElementThenNoSuchElementException() {
        SimpleArray<Integer> arr = new SimpleArray<>(5);
        arr.iterator().next();
    }

    @Test (expected = IllegalStateException.class)
    public void whenDeleteNonExistElementThenIllegalStateException() {
        SimpleArray<Integer> arr = new SimpleArray<>(5);
        arr.iterator().remove();
    }
}