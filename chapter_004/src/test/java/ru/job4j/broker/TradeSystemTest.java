package ru.job4j.broker;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TradeSystemTest {

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithBullBook() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, null, TypeAction.ADD, TypeOrder.ASK, 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNegativeId() {
        TradeSystem system = new TradeSystem();
        system.newOrder(-1, "", TypeAction.ADD, TypeOrder.ASK, 1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNegativePrice() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", TypeAction.ADD, TypeOrder.ASK, -1, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithNegativeVolume() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", TypeAction.ADD, TypeOrder.ASK, 1, -1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenNewOrderWithZeroVolume() {
        TradeSystem system = new TradeSystem();
        system.newOrder(1, "", TypeAction.ADD, TypeOrder.ASK, 1, 0);
    }

    @Test
    public void whenNewOrderThenToStringReturnDescriptionCup() {
        TradeSystem system = new TradeSystem();
        system.newOrder(2, "Google", TypeAction.ADD, TypeOrder.ASK, 1000, 5000);
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
    public void whenNewOrderDeleteThenToStringReturnDescriptionCup() {
        TradeSystem system = new TradeSystem();
        system.newOrder(3, "Google", TypeAction.ADD, TypeOrder.ASK, 1000, 5000);
        system.newOrder(3, "Google", TypeAction.DELETE, TypeOrder.ASK, 1000, 4500);
        String result = system.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Google%n"
                        + "Продажа Цена Покупка%n"
                        + "        1000 500    "
        );
        assertThat(result, is(expected));
    }
}