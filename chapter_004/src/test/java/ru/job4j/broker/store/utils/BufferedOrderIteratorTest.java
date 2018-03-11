package ru.job4j.broker.store.utils;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.broker.orders.SimpleOrder;
import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;
import ru.job4j.broker.store.*;
import ru.job4j.broker.store.utils.BufferedIterator;
import ru.job4j.broker.store.utils.BufferedOrderIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Buffered order iterator, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public class BufferedOrderIteratorTest {
    private final TypeOrdersWrapper<AskOrders> wrapper = new AskOrderWrapper();
    private OrderRepository<AskOrders> repository;
    private BufferedIterator<AskOrders> it;

    @Before
    public void setUp() {
        repository = new OrderRepository<>(wrapper);
        it = new BufferedOrderIterator<>(repository, wrapper);
    }

    @Test
    public void whenStoreEmptyThenIsStoreEmptyReturnTrue() {
        assertThat(it.storeIsEmpty(), is(true));
    }

    @Test
    public void whenStoreNotEmptyThenIsStoreEmptyReturnFalse() {
        repository.addOrder(new SimpleOrder(1, 10));
        assertThat(it.storeIsEmpty(), is(false));
    }

    @Test
    public void whenTakeNextThenVolumeChange() {
        repository.addOrder(new SimpleOrder(2, 15));
        repository.addOrder(new SimpleOrder(3, 18));
        assertThat(it.storeIsEmpty(), is(false));
        it.takeNext();
        int result = it.volume();
        int expected = 15;
        assertThat(result, is(expected));
        it.takeNext();
        result = it.volume();
        expected = 33;
        assertThat(result, is(expected));
    }

    @Test (expected = IllegalStateException.class)
    public void whenReceiveRequestedVolumeGreaterThanAvailableThenThrowException() {
        it.receive(100);
    }

    @Test (expected = IllegalStateException.class)
    public void whenReceiveNegativeRequestedVolumeThenThrowException() {
        repository.addOrder(new SimpleOrder(4, 21));
        it.takeNext();
        it.receive(-100);
    }

    @Test
    public void whenIteratorIsEmptyAndReceiveZeroVolumeThanReturnEmptyList() {
        it.takeNext();
        Collection<AskOrders> list = it.receive(0);
        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void whenReceiveLessAvailableVolumeThanReturnListWithRequiredVolumeAndExtraOrdersReturnedInStore() {
        AskOrders order1 = wrapper.wrap(new SimpleOrder(5, 25));
        AskOrders order2 = wrapper.wrap(new SimpleOrder(6, 15));
        AskOrders order3 = wrapper.wrap(new SimpleOrder(7, 35));
        repository.addOrder(order1);
        it.takeNext();
        repository.addOrder(new SimpleOrder(6, 30));
        it.takeNext();
        repository.addOrder(order3);
        it.takeNext();
        List<AskOrders> result = new ArrayList<>(it.receive(40));
        List<AskOrders> expected = Arrays.asList(
                order1,
                order2
        );
        assertThat(result, is(expected));
        assertThat(result.get(0).volume(), is(expected.get(0).volume()));
        assertThat(result.get(1).volume(), is(expected.get(1).volume()));
        assertThat(repository.withdrawOrder(), is(order2.order()));
        assertThat(repository.withdrawOrder(), is(order3.order()));
    }
}