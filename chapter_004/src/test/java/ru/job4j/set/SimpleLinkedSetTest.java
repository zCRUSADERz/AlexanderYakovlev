package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Simple linked set test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.02.2017
 */
public class SimpleLinkedSetTest {

    @Test
    public void whenAddSomeItemsWithDuplicatesThenSetDontHaveDuplicates() {
        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("1");
        set.add("1");
        set.add("2");
        set.add(null);
        set.add(null);
        Iterator<String> it = set.iterator();
        it.next();
        String result = it.next();
        assertThat(result, is("2"));
        it.next();
        assertThat(it.hasNext(), is(false));
    }
}