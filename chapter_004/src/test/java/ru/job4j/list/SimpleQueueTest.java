package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Simple queue test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.02.2017
 */
public class SimpleQueueTest {

    @Test
    public void whenPushElementsThenPollReturnedThisElementsInNormalOrder() {
        SimpleQueue<String> stack = new SimpleQueue<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        assertThat(stack.poll(), is("1"));
        assertThat(stack.poll(), is("2"));
        assertThat(stack.poll(), is("3"));
        assertThat(stack.poll(), nullValue());
    }
}