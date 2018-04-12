package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserAmountTest {

    @Test
    public void whenFromUsertransferToUser() {
        UserAmount from = new UserAmount(10);
        UserAmount to = new UserAmount(15);
        boolean result = from.transferTo(to, 1);
        assertThat(result, is(true));
        assertThat(from.get(), is(9));
        assertThat(to.get(), is(16));
        result = from.transferTo(to, 10);
        assertThat(result, is(false));
    }
}