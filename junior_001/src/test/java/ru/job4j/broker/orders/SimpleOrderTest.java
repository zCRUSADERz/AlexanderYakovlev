package ru.job4j.broker.orders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование простого ордера.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public class SimpleOrderTest {

    @Test
    public void whenNewOrderBasedOtherOrderThenNewOrderHaveSomeIdAndNewVolume() {
        SimpleOrder expected = new SimpleOrder(1, 100);
        SimpleOrder result = new SimpleOrder(expected, 120);
        assertThat(result, is(expected));
        assertThat(result.volume(), is(120));
    }

    @Test
    public void whenCallVolumeThenReturnVolumeThisOrder() {
        int result = new SimpleOrder(3, 90).volume();
        int expected = 90;
        assertThat(result, is(expected));
    }

    @Test
    public void whenOrderHaveZeroVolumeThenIsEmptyReturnTrue() {
        boolean result = new SimpleOrder(4, 0).isEmpty();
        assertThat(result, is(true));
    }

    @Test
    public void whenOrderHaveNotZeroVolumeThenIsEmptyReturnFalse() {
        boolean result = new SimpleOrder(4, 1).isEmpty();
        assertThat(result, is(false));
    }
}