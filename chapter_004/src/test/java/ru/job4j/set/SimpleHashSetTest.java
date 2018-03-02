package ru.job4j.set;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Simple Hash Set test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.03.2018
 */
public class SimpleHashSetTest {

    @Test
    public void whenAddSomeItemsWithDuplicatesThenSetDontHaveDuplicates() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        set.add(1);
        set.add(1);
        set.add(2);
        Iterator<Integer> it = set.iterator();
        it.next();
        Integer result = it.next();
        assertThat(result, is(2));
    }

    @Test
    public void whenAddMoreAvailableCapaciry() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        List<Integer> expected = new ArrayList<>(17);
        for (int i = 0; i < 17; i++) {
            set.add(i);
            expected.add(i);
        }
        Iterator<Integer> it = set.iterator();
        List<Integer> result = new ArrayList<>(17);
        while (it.hasNext()) {
            result.add(it.next());
        }
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddSomeItemsAndDeleteFrist() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        set.add(1);
        set.add(2);
        Iterator<Integer> it = set.iterator();
        it.next();
        it.remove();
        Integer result = it.next();
        Integer expected = 2;
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteNonExistentItemThenReturnFalse() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        boolean result = set.remove(0);
        assertThat(result, is(false));
    }

    @Test
    public void whenEmptySetThenIteratorHasNextReturnFalse() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        boolean result = set.iterator().hasNext();
        assertThat(result, is(false));
    }

    @Test (expected = IllegalStateException.class)
    public void whenIteratorNotReturnedItemThenRemoveThrowsException() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        set.iterator().remove();
    }

    @Test (expected = NoSuchElementException.class)
    public void whenEmptySetThenIteratorNextThrowsException() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        set.iterator().next();
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenChangeSetAfterGettingIteratorThenIteratorNextThrowsException() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        Iterator<Integer> it = set.iterator();
        set.add(1);
        it.next();
    }
}