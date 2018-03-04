package ru.job4j.broker.order;

import org.junit.Test;
import ru.job4j.broker.TypeOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * SimpleOrder test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public class SimpleOrderTest {

    @Test
    public void whenFirstOrderHaveMoreVolumeThanSecondThenExecuteReturnNewOrderWithFirstId() {
        SimpleOrder firstOrder = new SimpleOrder(1, TypeOrder.ASK, 10, 50);
        SimpleOrder secondOrder = new SimpleOrder(2, TypeOrder.BID, 10, 30);
        SimpleOrder result = firstOrder.execute(secondOrder);
        SimpleOrder expected = new SimpleOrder(1, TypeOrder.ASK, 10, 20);
        assertThat(result, is(expected));
        assertThat(result.price(), is(10));
        assertThat(result.volume(), is(20));
    }

    @Test
    public void whenSecondOrderHaveMoreVolumeThanFirstThenExecuteReturnNewOrderWithSecondId() {
        SimpleOrder firstOrder = new SimpleOrder(3, TypeOrder.ASK, 11, 25);
        SimpleOrder secondOrder = new SimpleOrder(4, TypeOrder.BID, 11, 40);
        SimpleOrder result = firstOrder.execute(secondOrder);
        SimpleOrder expected = new SimpleOrder(4, TypeOrder.BID, 11, 15);
        assertThat(result, is(expected));
        assertThat(result.price(), is(11));
        assertThat(result.volume(), is(15));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenTwoOrderWithDifferentPriceThenExecuteThrowIllegalArgumentException() {
        SimpleOrder firstOrder = new SimpleOrder(5, TypeOrder.ASK, 9, 3);
        SimpleOrder secondOrder = new SimpleOrder(6, TypeOrder.BID, 8, 4);
        firstOrder.execute(secondOrder);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenTwoOrderWithTheSameTypeThenExecuteThrowIllegalArgumentException() {
        SimpleOrder firstOrder = new SimpleOrder(7, TypeOrder.ASK, 9, 1);
        SimpleOrder secondOrder = new SimpleOrder(8, TypeOrder.ASK, 9, 5);
        firstOrder.execute(secondOrder);
    }

    @Test
    public void whenIdOrderEqualsThenEqualsReturnTrue() {
        SimpleOrder firstOrder = new SimpleOrder(9, TypeOrder.ASK, 13, 10);
        SimpleOrder secondOrder = new SimpleOrder(9, TypeOrder.BID, 12, 70);
        boolean result = firstOrder.equals(secondOrder);
        assertThat(result, is(true));
    }

    @Test
    public void whenIdOrdersNotEqualsThenEqualsReturnFalse() {
        SimpleOrder firstOrder = new SimpleOrder(10, TypeOrder.ASK, 14, 9);
        SimpleOrder secondOrder = new SimpleOrder(11, TypeOrder.ASK, 14, 9);
        boolean result = firstOrder.equals(secondOrder);
        assertThat(result, is(false));
    }

    @Test
    public void equalsTest() {
        SimpleOrder firstOrder = new SimpleOrder(12, TypeOrder.ASK, 15, 20);
        boolean result = firstOrder.equals(null);
        assertThat(result, is(false));
        result = firstOrder.equals(firstOrder);
        assertThat(result, is(true));
        result = firstOrder.equals(new Object());
        assertThat(result, is(false));
    }

    @Test
    public void hashTest() {
        SimpleOrder firstOrder = new SimpleOrder(13, TypeOrder.ASK, 13, 10);
        SimpleOrder secondOrder = new SimpleOrder(13, TypeOrder.BID, 14, 15);
        assertThat(firstOrder.equals(secondOrder), is(true));
        int result = firstOrder.hashCode();
        int expected = secondOrder.hashCode();
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewOrderThenToStringReturnDescription() {
        String result = new SimpleOrder(14, TypeOrder.ASK, 24, 37).toString();
        String expected = "ASK order id: 14, price: 24, volume: 37";
        assertThat(result, is(expected));
        result = new SimpleOrder(15, TypeOrder.BID, 27, 39).toString();
        expected = "BID order id: 15, price: 27, volume: 39";
        assertThat(result, is(expected));
    }

    @Test
    public void whenOrderDeleteHaveMoreVolumeThenReturnOrderWithZeroVolume() {
        SimpleOrder removableOrder = new SimpleOrder(16, TypeOrder.ASK, 34, 10);
        SimpleOrder orderDelete = new SimpleOrder(16, TypeOrder.ASK, 34, 15);
        SimpleOrder resultOrder = removableOrder.delete(orderDelete);
        int resultVolume = resultOrder.volume();
        int expected = 0;
        assertThat(resultVolume, is(expected));
        assertThat(resultOrder, is(new SimpleOrder(16, TypeOrder.ASK, 34, 0)));
        assertThat(resultOrder.type(), is(TypeOrder.ASK));
        assertThat(resultOrder.price(), is(34));
    }

    @Test
    public void whenOrderDeleteHaveLessVolumeThenReturnOrderWithTheRemainingVolume() {
        SimpleOrder removableOrder = new SimpleOrder(17, TypeOrder.ASK, 34, 45);
        SimpleOrder orderDelete = new SimpleOrder(17, TypeOrder.ASK, 34, 15);
        SimpleOrder resultOrder = removableOrder.delete(orderDelete);
        int resultVolume = resultOrder.volume();
        int expected = 30;
        assertThat(resultVolume, is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenOrderDeleteWithAnotherId() {
        SimpleOrder removableOrder = new SimpleOrder(18, TypeOrder.ASK, 34, 45);
        SimpleOrder orderDelete = new SimpleOrder(19, TypeOrder.ASK, 34, 15);
        removableOrder.delete(orderDelete);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenOrderDeleteWithAnotherType() {
        SimpleOrder removableOrder = new SimpleOrder(20, TypeOrder.ASK, 34, 45);
        SimpleOrder orderDelete = new SimpleOrder(20, TypeOrder.BID, 34, 15);
        removableOrder.delete(orderDelete);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenOrderDeleteWithAnotherPrice() {
        SimpleOrder removableOrder = new SimpleOrder(21, TypeOrder.ASK, 34, 45);
        SimpleOrder orderDelete = new SimpleOrder(22, TypeOrder.ASK, 38, 15);
        removableOrder.delete(orderDelete);
    }
}