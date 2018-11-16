package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * String contains test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class StringContainsTest {

    @Test
    public void whenOriginStringContainsSubStringThenTrue() {
        StringContains stringContains = new StringContains();
        String origin = "Привет, Бот!";
        String sub = "от!";
        boolean result = stringContains.contains(origin, sub);
        assertThat(result, is(true));
    }

    @Test
    public void whenOriginStringNotContainsSubStringThenFalse() {
        StringContains stringContains = new StringContains();
        String origin = "Привет, Бот!";
        String sub = "c";
        boolean result = stringContains.contains(origin, sub);
        assertThat(result, is(false));
    }
}