package ru.job4j;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Coffe machine test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.01.2017
 * @version 1.0
 */
public class CoffeeMachineTest {

    @Test
    public void whenGiveFiftyAndPriceThirtyTwoThenTenFiveTwoOneCoins() {
        CoffeeMachine machine = new CoffeeMachine();
        int[] result = machine.changes(50, 32);
        int[] expected = new int[] {10, 5, 2, 1};
        assertThat(result, is(expected));
    }

    @Test
    public void whenGiveNothingAndPriceThirtyFiveThenNothing() {
        CoffeeMachine machine = new CoffeeMachine();
        int[] result = machine.changes(0, 35);
        int[] expected = new int[] {};
        assertThat(result, is(expected));
    }
}