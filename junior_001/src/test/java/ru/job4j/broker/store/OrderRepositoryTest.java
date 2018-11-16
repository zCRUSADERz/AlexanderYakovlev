package ru.job4j.broker.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.SimpleOrder;
import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OrderRepositoryTest {
    private final TypeOrdersWrapper<AskOrders> wrapper = new AskOrderWrapper();
    private OrderRepository<AskOrders> repository;

    @Before
    public void setUp() {
        repository = new OrderRepository<>(wrapper);
    }

    @Test
    public void whenWithdrawOrderThenRepositoryDontHaveThisOrder() {
        repository.addOrder(new SimpleOrder(1, 10));
        repository.addOrder(new SimpleOrder(2, 20));
        repository.addOrder(new SimpleOrder(3, 30));
        repository.withdrawOrder();
        int result = repository.volume();
        int expected = 50;
        assertThat(result, is(expected));
        assertThat(repository.isEmpty(), is(false));
    }

    @Test
    public void whenWithdrawThenRepositoryReturnFirstOrderInCollection() {
        repository.addOrder(new SimpleOrder(4, 40));
        assertThat(repository.volume(), is(40));
        Order expected = new SimpleOrder(5, 50);
        repository.addOrder(expected);
        repository.withdrawOrder();
        assertThat(repository.volume(), is(50));
        Order result = repository.withdrawOrder();
        assertThat(result, is(expected));
        assertThat(repository.isEmpty(), is(true));
        assertThat(repository.volume(), is(0));
    }

    @Test
    public void whenWithdrawExistOrderThenRepositoryDontHaveThisOrder() {
        repository.addOrder(new SimpleOrder(6, 60));
        Order expected = new SimpleOrder(7, 170);
        repository.addOrder(expected);
        repository.addOrder(new SimpleOrder(8, 100));
        Order result = repository.withdrawOrder(expected);
        assertThat(result, is(expected));
        assertThat(repository.isEmpty(), is(false));
        assertThat(repository.volume(), is(160));
    }

    @Test
    public void whenWithdrawNotExistOrderThenWithdrawReturnOrderWithZeroVolume() {
        repository.addOrder(new SimpleOrder(10, 210));
        repository.addOrder(new SimpleOrder(11, 250));
        repository.addOrder(new SimpleOrder(12, 5));
        Order result = repository.withdrawOrder(new SimpleOrder(13, 7));
        Order expected = new Order.EmptyOrder();
        assertThat(result, is(expected));
        assertThat(result.volume(), is(0));
        assertThat(repository.isEmpty(), is(false));
        assertThat(repository.volume(), is(465));
    }

    @Test
    public void whenRepositoryNotEmptyThenToStringReturnOrdersString() {
        repository.addOrder(new SimpleOrder(13, 230));
        repository.addOrder(new SimpleOrder(14, 285));
        String result = repository.toString();
        String expected = String.format(
                "order id: 13, volume: 230%n"
                        + "order id: 14, volume: 285"
        );
        assertThat(result, is(expected));
    }
}