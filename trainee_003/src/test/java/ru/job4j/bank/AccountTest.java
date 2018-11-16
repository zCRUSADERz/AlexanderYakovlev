package ru.job4j.bank;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Account test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class AccountTest {

    @Test
    public void whenTransferOfAvailableAmountThenReturnTrue() {
        Account first = new Account(10, "1");
        Account second = new Account(0, "2");
        boolean result = first.transfer(second, 5);
        assertThat(result, is(true));
    }

    @Test
    public void whenTransferOfUnavailableAmountThenReturnFalse() {
        Account first = new Account(0, "1");
        Account second = new Account(0, "2");
        boolean result = first.transfer(second, 5);
        assertThat(result, is(false));
    }

    @Test
    public void whenFirstTransferFiveToSecondThenSecondValueIncreaseByFive() {
        Account first = new Account(10, "1");
        Account second = new Account(0, "2");
        first.transfer(second, 5);
        double result = second.getValue();
        double expected = 5;
        assertThat(result, is(expected));
    }

    @Test
    public void whenTransferNegativeAmountThenReturnFalse() {
        Account first = new Account(10, "1");
        Account second = new Account(0, "2");
        boolean result = first.transfer(second, -5);
        assertThat(result, is(false));
    }

    @Test
    public void whenEqualsTwoAccountThenEqualsReturnTrue() {
        Account first = new Account(0, "1");
        Account second = new Account(0, "1");
        boolean result = (first.equals(second) && first.equals(first));
        assertThat(result, is(true));
    }

    @Test
    public void whenNotEqualsTwoAccountThenEqualsReturnFalse() {
        Account first = new Account(0, "1");
        Account second = new Account(0, "2");
        boolean result = first.equals(second);
        assertThat(result, is(false));
    }
}