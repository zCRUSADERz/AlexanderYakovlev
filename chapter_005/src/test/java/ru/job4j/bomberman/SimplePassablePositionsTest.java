package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Проходимые позиции на игровой доске. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.06.2018
 */
public class SimplePassablePositionsTest {
    private final PassablePositions passablePositions = new SimplePassablePositions(new BoardPositions(2));

    @Test
    public void whenIsPassableCoordinate() {
        boolean result = passablePositions.isPassableCoordinate(-1, -1);
        boolean expected = false;
        assertThat(result, is(expected));
        result = passablePositions.isPassableCoordinate(-1, 0);
        assertThat(result, is(expected));
        result = passablePositions.isPassableCoordinate(1, 2);
        assertThat(result, is(expected));
        result = passablePositions.isPassableCoordinate(2, 2);
        assertThat(result, is(expected));
        result = passablePositions.isPassableCoordinate(0, 0);
        expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAllPassablePosition() {
        Collection<Position> expected = Arrays.asList(
                new Position(1, 0),
                new Position(0, 0),
                new Position(0, 1)
        );
        Collection<Position> result = passablePositions.allPassablePositions();
        assertThat(result, is(expected));
    }

    @Test
    public void whenAllImpassablePosition() {
        Collection<Position> expected = Arrays.asList(
                new Position(1, 1)
        );
        Collection<Position> result = passablePositions.impassablePositions();
        assertThat(result, is(expected));
    }
}