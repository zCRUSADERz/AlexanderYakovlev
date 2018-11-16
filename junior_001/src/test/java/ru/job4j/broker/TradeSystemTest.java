package ru.job4j.broker;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TradeSystemTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenNewOrderWithBullBook() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, null, "Add", "Ask", 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNegativeId() {
        TradeSystem system = new TradeSystem();
        system.newOrder(-1, "", "ADD", "Ask", 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNegativePrice() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", "ADD", "Ask", -1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNegativeVolume() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", "ADD", "Ask", 1, -1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithZeroVolume() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", "ADD", "Ask", 1, 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNullAction() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", null, "Ask", 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithInvalidAction() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", "ADDd", "Ask", 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNullType() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", "ADD", null, 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithInvalidType() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", "ADD", "Asks", 1, 1);
    }

    @Test
    public void whenNewAskOrderThenToStringReturnDescription() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "Google", "ADD", "Ask", 1000, 5000);
        String result = system.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Google%n"
                        + "Продажа Цена Покупка%n"
                        + "        1000 5000   "
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewBidOrderThenToStringReturnDescription() {
        TradeSystem system = new TradeSystem();
        system.newOrder(2, "Google", "ADD", "Bid", 2000, 4000);
        String result = system.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Google%n"
                        + "Продажа Цена Покупка%n"
                        + "   4000 2000"
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewAskOrderDeleteThenToStringReturnDescription() {
        TradeSystem system = new TradeSystem();
        system.newOrder(3, "Google", "ADD", "Ask", 1000, 5000);
        system.newOrder(3, "Google", "DELETE", "Ask", 1000, 4500);
        String result = system.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Google%n"
                        + "Продажа Цена Покупка%n"
                        + "        1000 500    "
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewBidOrderDeleteThenToStringReturnDescription() {
        TradeSystem system = new TradeSystem();
        system.newOrder(4, "Google", "ADD", "Bid", 3000, 7000);
        system.newOrder(4, "Google", "DELETE", "Bid", 3000, 5500);
        String result = system.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Google%n"
                        + "Продажа Цена Покупка%n"
                        + "   1500 3000"
        );
        assertThat(result, is(expected));
    }

}