package ru.job4j.bomberman;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование местоположения на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class BoardPositionTest {

    @Test
    public void whenCalculateNewPositionThenReturnNewPosition() {
        BoardPosition current = new BoardPosition(3, 5);
        BoardPosition newPosition = current.calculateNewPosition(DirectionOfMove.DOWN);
        assertThat(newPosition.xCoordinate(), is(3));
        assertThat(newPosition.yCoordinate(), is(4));
    }
}