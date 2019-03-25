package ru.job4j.switcher;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class NumberStringTest {

    @Test
    public void whenAttachOneHundredTwentyThree() {
        final NumberString numberString = new NumberString();
        numberString.attach(123);
        assertThat(numberString.string(), is("123"));
    }

    @Test
    public void whenAttachNumberThenAttachedToTheEndOfTheLine() {
        final NumberString numberString = new NumberString(
                new AtomicReference<>("Numbers: ")
        );
        numberString.attach(5);
        final String expected = "Numbers: 5";
        final String result = numberString.string();
        assertThat(result, is(expected));
    }
}