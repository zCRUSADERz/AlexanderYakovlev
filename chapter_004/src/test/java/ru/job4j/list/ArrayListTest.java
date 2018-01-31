package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Array list test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 31.01.2017
 */
public class ArrayListTest {

    @Test (expected = IllegalArgumentException.class)
    public void whenNewArrayListWithNegativeCapacityThenException() {
        new ArrayList<>(-1);
    }

    @Test
    public void whenAddAndGetElementThenReturnThisElement() {
        ArrayList<String> list = new ArrayList<>();
        String str = "1";
        list.add(str);
        String result = list.get(0);
        assertThat(result, is(str));
    }

    @Test
    public void whenAddMoreCapacityThenNothing() {
        ArrayList<Object> list = new ArrayList<>(15);
        for (int i = 0; i < 16; i++) {
            list.add(new Object());
        }
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenGetExistElementThenIndexOutOfBoundException() {
        ArrayList<String> list = new ArrayList<>(1);
        list.get(1);
    }

    @Test
    public void whenAddAndIteratorNextElementThenReturnThisElement() {
        ArrayList<String> list = new ArrayList<>();
        String str = "1";
        list.add(str);
        Iterator<String> it = list.iterator();
        String result = it.next();
        assertThat(result, is(str));
    }

    @Test
    public void whenAddAndIteratorHasNextThenReturnTrue() {
        ArrayList<String> list = new ArrayList<>();
        String str = "1";
        list.add(str);
        Iterator<String> it = list.iterator();
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenListEmptyAndIteratorNextThenNoSuchElementException() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> it = list.iterator();
        String result = it.next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void whenIteratorRemoveThenException() {
        new ArrayList<>().iterator().remove();
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenGetIteratorAndChangedListThenException() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> it = list.iterator();
        list.add("1");
        it.next();
    }
}