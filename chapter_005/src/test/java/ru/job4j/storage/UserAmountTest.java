package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserAmountTest {

    @Test
    public void whenFromUserIdLessThanToUserThenTransferTo() {
        UserAmount from = new UserAmount(1, 10);
        UserAmount to = new UserAmount(2, 15);
        boolean result = from.transferTo(to, 1);
        assertThat(result, is(true));
        assertThat(from.get(), is(9));
        assertThat(to.get(), is(16));
        result = from.transferTo(to, 10);
        assertThat(result, is(false));
    }

    @Test
    public void whenFromUserIdMoreThanToUserThenTransferTo() {
        UserAmount from = new UserAmount(4, 20);
        UserAmount to = new UserAmount(3, 25);
        boolean result = from.transferTo(to, 15);
        assertThat(result, is(true));
        assertThat(from.get(), is(5));
        assertThat(to.get(), is(40));
        result = from.transferTo(to, 25);
        assertThat(result, is(false));
    }
}