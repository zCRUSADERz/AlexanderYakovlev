package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Linked list.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.02.2017
 */
public class LinkedListTest {
    private LinkedList<String> list;

    @Before
    public void setList() {
        list = new LinkedList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
    }

    @Test
    public void whenAddAndGetElementThenReturnThisElement() {
        String result = list.get(1);
        assertThat(result, is("1"));
    }

    @Test
    public void whenAddSeveralElementsAndGetLastElementThenReturnThisElement() {
        list.add("1");
        list.add("2");
        String str = "3";
        list.add(str);
        String result = list.get(4);
        assertThat(result, is("4"));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenGetExistElementThenIndexOutOfBoundException() {
        list.get(10);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenGetByNegativeIndexThenIndexOutOfBoundException() {
        list.get(-1);
    }

    @Test
    public void whenRemoveThenReturnRemovedElement() {
        String result = list.remove(0);
        assertThat(result, is("0"));
    }

    @Test
    public void whenAddAndIteratorHasNextThenReturnTrue() {
        Iterator<String> it = list.iterator();
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }

    @Test
    public void whenIteratorHasNextThenReturnFalse() {
        list = new LinkedList<>();
        Iterator<String> it = list.iterator();
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }

    @Test
    public void whenAddAndIteratorNextElementThenReturnThisElement() {
        Iterator<String> it = list.iterator();
        String result = it.next();
        assertThat(result, is("0"));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenListEmptyAndIteratorNextThenNoSuchElementException() {
        list = new LinkedList<>();
        list.iterator().next();
    }

    @Test (expected = IllegalStateException.class)
    public void whenIteratorRemoveThenException() {
        new LinkedList<>().iterator().remove();
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenGetIteratorAndChangeListThenRemoveThrowsException() {
        Iterator<String> it = list.iterator();
        list.add("");
        it.remove();
    }

    @Test
    public void whenIteratorRemoveFristThenNextReturnNext() {
        Iterator<String> it = list.iterator();
        it.next();
        it.remove();
        String result = it.next();
        assertThat(result, is("1"));
    }

    @Test
    public void whenIteratorRemoveFromTheMiddleThenNextReturnNext() {
        Iterator<String> it = list.iterator();
        it.next();
        it.next();
        it.remove();
        String result = it.next();
        assertThat(result, is("2"));
    }

    @Test
    public void whenIteratorRemoveLastThenHasNextReturnFalse() {
        Iterator<String> it = list.iterator();
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        it.remove();
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }
}