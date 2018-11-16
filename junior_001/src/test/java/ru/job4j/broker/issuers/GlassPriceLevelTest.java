package ru.job4j.broker.issuers;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.broker.orders.*;
import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.BidOrders;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.BidOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Уровень цены в биржевом стакане, тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 06.03.2018
 */
public class GlassPriceLevelTest {
    private int nextPrice = 10;
    private TypeOrdersWrapper<AskOrders> askWrapper = new AskOrderWrapper();
    private TypeOrdersWrapper<BidOrders> bidWrapper = new BidOrderWrapper();

    @Before
    public void setUp() {
        nextPrice++;
    }

    @Test
    public void whenAddAskOrder() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(askWrapper.wrap(new SimpleOrder(1, 15)));
        String result = level.toString();
        String expected = String.format("        %-4d 15     ", nextPrice);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddNewBidOrderThenToStringReturnResultWithOrder() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(bidWrapper.wrap(new SimpleOrder(2, 5)));
        String result = level.toString();
        String expected = String.format("      5 %-4d", nextPrice);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddTwoNewBidOrderThenToStringReturnResultWithThisOrders() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(bidWrapper.wrap(new SimpleOrder(3, 777)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(4, 936)));
        String result = level.toString();
        String expected = String.format("   1713 %-4d", nextPrice);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddAskAndBidOrderThenLevelHaveOneAskOrBidOrder() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(askWrapper.wrap(new SimpleOrder(5, 700)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(6, 300)));
        String result = level.toString();
        String expected = String.format("        %-4d 400    ", nextPrice);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddAskAndBidOrderWithSameVolumeThenLevelHaveNoOrder() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(askWrapper.wrap(new SimpleOrder(18, 700)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(19, 700)));
        String result = level.toString();
        String expected = "";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddMoreDifferentOrdersThenOrderExecutedInOrderOfAddition() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(askWrapper.wrap(new SimpleOrder(7, 60)));
        level.addOrder(askWrapper.wrap(new SimpleOrder(8, 30)));
        level.addOrder(askWrapper.wrap(new SimpleOrder(9, 100)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(10, 85)));
        String result = level.ordersToString();
        String expected = String.format(
                "Ask orders:%n"
                        + "order id: 8, volume: 5%n"
                        + "order id: 9, volume: 100%n"
                        + "Bid orders: no one"
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewDeleteAskOrderThenDeleteOrderWithSameId() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(askWrapper.wrap(new SimpleOrder(11, 60)));
        level.addOrder(askWrapper.wrap(new SimpleOrder(12, 30)));
        level.deleteOrder(askWrapper.wrap(new SimpleOrder(11, 60)));
        String result = level.ordersToString();
        String expected = String.format(
                "Ask orders:%n"
                        + "order id: 12, volume: 30%n"
                        + "Bid orders: no one"
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewDeleteBidOrderThenDeleteOrderWithSameId() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(bidWrapper.wrap(new SimpleOrder(13, 70)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(14, 20)));
        level.deleteOrder(bidWrapper.wrap(new SimpleOrder(13, 70)));
        String result = level.ordersToString();
        String expected = String.format(
                "Ask orders: no one%n"
                        + "Bid orders:%n"
                        + "order id: 14, volume: 20"
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeletePartOfOrderThenNewOrderInsertedInSameIndex() {
        GlassPriceLevel<AskOrders, BidOrders> level = new GlassPriceLevel<>(askWrapper, bidWrapper, nextPrice);
        level.addOrder(bidWrapper.wrap(new SimpleOrder(15, 35)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(16, 45)));
        level.addOrder(bidWrapper.wrap(new SimpleOrder(17, 55)));
        level.deleteOrder(bidWrapper.wrap(new SimpleOrder(16, 15)));
        String result = level.ordersToString();
        String expected = String.format(
                "Ask orders: no one%n"
                        + "Bid orders:%n"
                        + "order id: 15, volume: 35%n"
                        + "order id: 17, volume: 55%n"
                        + "order id: 16, volume: 30"
        );
        assertThat(result, is(expected));
    }
}