package ru.job4j.broker.orderstore;

import org.junit.Test;
import ru.job4j.broker.TypeOrder;
import ru.job4j.broker.order.SimpleOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Price level test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public class PriceLevelTest {

    @Test
    public void whenAddNewAskOrderThenToStringReturnResultWithOrder() {
        PriceLevel level = new PriceLevel(10);
        level.addAskOrder(new SimpleOrder(1, TypeOrder.ASK, 10, 15));
        String result = level.toString();
        String expected = "        10   15     ";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddNewBidOrderThenToStringReturnResultWithOrder() {
        PriceLevel level = new PriceLevel(11);
        level.addBidOrder(new SimpleOrder(2, TypeOrder.BID, 11, 5));
        String result = level.toString();
        String expected = "      5 11  ";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddTwoNewBidOrderThenToStringReturnResultWithThisOrders() {
        PriceLevel level = new PriceLevel(12);
        level.addBidOrder(new SimpleOrder(3, TypeOrder.BID, 12, 777));
        level.addBidOrder(new SimpleOrder(4, TypeOrder.BID, 12, 936));
        String result = level.toString();
        String expected = "   1713 12  ";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddAskAndBidOrderThenCupHaveOneAskOrBidOrder() {
        PriceLevel level = new PriceLevel(13);
        level.addAskOrder(new SimpleOrder(5, TypeOrder.ASK, 13, 700));
        level.addBidOrder(new SimpleOrder(6, TypeOrder.BID, 13, 300));
        String result = level.toString();
        String expected = "        13   400    ";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddAskAndBidOrderWithSameVolumeThenCupHaveNoOrder() {
        PriceLevel level = new PriceLevel(13);
        level.addAskOrder(new SimpleOrder(18, TypeOrder.ASK, 13, 700));
        level.addBidOrder(new SimpleOrder(19, TypeOrder.BID, 13, 700));
        String result = level.toString();
        String expected = "";
        assertThat(result, is(expected));
    }

    @Test
    public void whenAddMoreDifferentOrdersThenOrderExecutedInOrderOfAddition() {
        PriceLevel level = new PriceLevel(14);
        level.addAskOrder(new SimpleOrder(7, TypeOrder.ASK, 14, 60));
        level.addAskOrder(new SimpleOrder(8, TypeOrder.ASK, 14, 30));
        level.addAskOrder(new SimpleOrder(9, TypeOrder.ASK, 14, 100));
        level.addBidOrder(new SimpleOrder(10, TypeOrder.BID, 14, 85));
        String result = level.askOrdersToString();
        String expected = String.format(
                "ASK order id: 8, price: 14, volume: 5%n"
                        + "ASK order id: 9, price: 14, volume: 100"
        );
        assertThat(result, is(expected));
        assertThat(level.bidOrdersToString(), is(""));
    }

    @Test
    public void whenNewDeleteAskOrderThenDeleteOrderWithSameId() {
        PriceLevel level = new PriceLevel(15);
        level.addAskOrder(new SimpleOrder(11, TypeOrder.ASK, 15, 60));
        level.addAskOrder(new SimpleOrder(12, TypeOrder.ASK, 15, 30));
        level.deleteAskOrder(new SimpleOrder(11, TypeOrder.ASK, 15, 60));
        String result = level.askOrdersToString();
        String expected = "ASK order id: 12, price: 15, volume: 30";
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewDeleteBidOrderThenDeleteOrderWithSameId() {
        PriceLevel level = new PriceLevel(16);
        level.addBidOrder(new SimpleOrder(13, TypeOrder.BID, 16, 60));
        level.addBidOrder(new SimpleOrder(14, TypeOrder.BID, 16, 30));
        level.deleteBidOrder(new SimpleOrder(13, TypeOrder.BID, 16, 60));
        String result = level.bidOrdersToString();
        String expected = "BID order id: 14, price: 16, volume: 30";
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeletePartOfAnOrderThenNewOrderInsertedInSameIndex() {
        PriceLevel level = new PriceLevel(16);
        level.addBidOrder(new SimpleOrder(15, TypeOrder.BID, 16, 60));
        level.addBidOrder(new SimpleOrder(16, TypeOrder.BID, 16, 30));
        level.addBidOrder(new SimpleOrder(17, TypeOrder.BID, 16, 30));
        level.deleteBidOrder(new SimpleOrder(16, TypeOrder.BID, 16, 10));
        String result = level.bidOrdersToString();
        String expected = String.format(
                "BID order id: 15, price: 16, volume: 60%n"
                        + "BID order id: 16, price: 16, volume: 20%n"
                        + "BID order id: 17, price: 16, volume: 30"
        );
        assertThat(result, is(expected));
    }
}