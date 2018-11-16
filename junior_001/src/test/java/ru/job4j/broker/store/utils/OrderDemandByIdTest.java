package ru.job4j.broker.store.utils;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.SimpleOrder;
import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;
import ru.job4j.broker.store.utils.OrderDemandById;
import ru.job4j.broker.store.OrderRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Order demand by Id, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 06.03.2018
 */
public class OrderDemandByIdTest {
    private final TypeOrdersWrapper<AskOrders> wrapper = new AskOrderWrapper();
    private OrderRepository<AskOrders> repository;

    @Before
    public void setUp() {
        repository = new OrderRepository<>(wrapper);
    }

    @Test
    public void whenStoreHaveRequestedOrderThenRequestReturnThisOrder() {
        AskOrders expected = wrapper.wrap(new SimpleOrder(1, 20));
        repository.addOrder(expected);
        OrderDemandById<AskOrders> orderDemand = new OrderDemandById<>(
                wrapper.wrap(new SimpleOrder(1, 20)), repository, wrapper
        );
        AskOrders result = orderDemand.request();
        assertThat(result, is(expected));
        assertThat(result.volume(), is(expected.volume()));
        assertThat(repository.isEmpty(), is(true));
    }

    @Test
    public void whenStoreDontHaveRequestedOrderThenRequestReturnEmptySimpleOrder() {
        repository.addOrder(new SimpleOrder(2, 10));
        OrderDemandById<AskOrders> orderDemand = new OrderDemandById<>(
                wrapper.wrap(new SimpleOrder(3, 25)), repository, wrapper
        );
        AskOrders result = orderDemand.request();
        AskOrders expected = wrapper.wrap(new Order.EmptyOrder());
        assertThat(result, is(expected));
        assertThat(result.volume(), is(0));
        assertThat(repository.isEmpty(), is(false));
    }

    @Test
    public void whenStoreHaveRequestedOrderWithMoreVolumeThenRequestReturnOrderWithRequireVolume() {
        AskOrders expected = wrapper.wrap(new SimpleOrder(4, 35));
        repository.addOrder(expected);
        OrderDemandById<AskOrders> orderDemand = new OrderDemandById<>(
                wrapper.wrap(new SimpleOrder(4, 5)), repository, wrapper
        );
        AskOrders result = orderDemand.request();
        assertThat(result, is(expected));
        assertThat(result.volume(), is(5));
        assertThat(repository.isEmpty(), is(false));
    }
}