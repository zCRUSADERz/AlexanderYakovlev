package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Turn test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class TurnTest {

    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[] array = {1, 2, 3, 4, 5};
        int[] result = turn.back(array);
        int[] expected = {5, 4, 3, 2, 1};
        assertThat(result, is(expected));
    }

    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        Turn turn = new Turn();
        int[] array = {1, 2, 3, 4, 5, 6};
        int[] result = turn.back(array);
        int[] expected = {6, 5, 4, 3, 2, 1};
        assertThat(result, is(expected));
    }
}
