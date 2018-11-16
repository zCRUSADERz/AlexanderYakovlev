package ru.job4j.broker.orders.utils;

import org.junit.Test;
import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.OrderPartition;
import ru.job4j.broker.orders.SimpleOrder;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Делитель простых ордеров, тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public class OrderPartitionTest {

    @Test (expected = IllegalStateException.class)
    public void whenRequiredVolumeGreaterThanOrderVolumeThenDivideThrowException() {
        OrderPartition partition = new OrderPartition(new SimpleOrder(1, 10), 50);
        partition.divide();
    }

    @Test (expected = IllegalStateException.class)
    public void whenRequiredVolumeNegativeThenDivideThrowException() {
        OrderPartition partition = new OrderPartition(new SimpleOrder(2, 20), -10);
        partition.divide();
    }

    @Test
    public void whenDivideThenFirstOrderHaveRequiredVolume() {
        OrderPartition partition = new OrderPartition(new SimpleOrder(3, 30), 20);
        Iterator<Order> it = partition.divide();
        Order expected = new SimpleOrder(3, 20);
        Order result = it.next();
        assertThat(result, is(expected));
        expected = new SimpleOrder(3, 10);
        result = it.next();
        assertThat(result, is(expected));
        assertThat(it.hasNext(), is(false));
    }
}