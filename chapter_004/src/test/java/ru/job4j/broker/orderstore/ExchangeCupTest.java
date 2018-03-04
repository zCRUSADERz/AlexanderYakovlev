package ru.job4j.broker.orderstore;

import org.junit.Test;
import ru.job4j.broker.TypeOrder;
import ru.job4j.broker.order.SimpleOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExchangeCupTest {

    @Test
    public void whenAddSomeOrdersWithDifferentPriceThenToStringReturnStockCup() {
        ExchangeCup cup = new ExchangeCup("google");
        cup.orderStore(1).addAskOrder(new SimpleOrder(1, TypeOrder.ASK, 1, 10));
        cup.orderStore(2).addBidOrder(new SimpleOrder(2, TypeOrder.BID, 2, 7));
        cup.orderStore(3);
        String result = cup.toString();
        String expected = String.format(
                "google%n"
                        + "Продажа Цена Покупка%n"
                        + "      7 2   %n"
                        + "        1    10     "
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetNonExistentStoreThenReturnNewStore() {
        ExchangeCup cup = new ExchangeCup("google");
        assertThat(cup.toString(), is("google"));
        cup.orderStore(2).addBidOrder(new SimpleOrder(2, TypeOrder.BID, 2, 7));
        String result = cup.toString();
        String expected = String.format(
                "google%n"
                        + "Продажа Цена Покупка%n"
                        + "      7 2   "
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetExistStoreThenReturnThisStore() {
        ExchangeCup cup = new ExchangeCup("google");
        OrderStore expected = cup.orderStore(1);
        OrderStore result = cup.orderStore(1);
        assertThat(result, is(expected));
    }
}