package ru.job4j.bomberman;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование направления движения.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class DirectionOfMoveTest {

    @Test
    public void testUpDirection() {
        assertThat(DirectionOfMove.UP.x(), is(0));
        assertThat(DirectionOfMove.UP.y(), is(1));
    }

    @Test
    public void testLeftDirection() {
        assertThat(DirectionOfMove.LEFT.x(), is(-1));
        assertThat(DirectionOfMove.LEFT.y(), is(0));
    }

    @Test
    public void testRightDirection() {
        assertThat(DirectionOfMove.RIGHT.x(), is(1));
        assertThat(DirectionOfMove.RIGHT.y(), is(0));
    }

    @Test
    public void testDownDirection() {
        assertThat(DirectionOfMove.DOWN.x(), is(0));
        assertThat(DirectionOfMove.DOWN.y(), is(-1));
    }
}