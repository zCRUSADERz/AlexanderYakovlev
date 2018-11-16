package ru.job4j.map;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * Simple Hash Map test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.03.2018
 */
public class SimpleHashMapTest {

    @Test
    public void whenAddSomeItemsWithDuplicatesThenMapDontHaveDuplicates() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        map.insert(1, 0);
        map.insert(1, 0);
        map.insert(2, 0);
        Iterator<SimpleHashMap.Node<Integer, Integer>> it = map.iterator();
        it.next();
        Integer result = it.next().key();
        assertThat(result, is(2));
    }

    @Test
    public void whenAddItemsThenGetReturnValue() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        map.insert(1, 0);
        Integer result = map.get(1);
        assertThat(result, is(0));
        assertThat(map.get(-1), nullValue());
    }

    @Test
    public void whenAddMoreAvailableCapacity() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        List<Integer> expected = new ArrayList<>(17);
        for (int i = 0; i < 17; i++) {
            map.insert(i, 0);
            expected.add(i);
        }
        Iterator<SimpleHashMap.Node<Integer, Integer>> it = map.iterator();
        List<Integer> result = new ArrayList<>(17);
        while (it.hasNext()) {
            result.add(it.next().key());
        }
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddSomeItemsAndDeleteFrist() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        map.insert(1, 0);
        map.insert(2, 0);
        Iterator<SimpleHashMap.Node<Integer, Integer>> it = map.iterator();
        it.next();
        it.remove();
        Integer result = it.next().key();
        Integer expected = 2;
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteNonExistentItemThenReturnFalse() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        boolean result = map.delete(0);
        assertThat(result, is(false));
    }

    @Test
    public void whenEmptyMapThenIteratorHasNextReturnFalse() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        boolean result = map.iterator().hasNext();
        assertThat(result, is(false));
    }

    @Test (expected = IllegalStateException.class)
    public void whenIteratorNotReturnedItemThenRemoveThrowsException() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        map.iterator().remove();
    }

    @Test (expected = NoSuchElementException.class)
    public void whenEmptyMapThenIteratorNextThrowsException() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        map.iterator().next();
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenChangeMapAfterGettingIteratorThenIteratorNextThrowsException() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();
        Iterator<SimpleHashMap.Node<Integer, Integer>> it = map.iterator();
        map.insert(1, 1);
        it.next();
    }
}