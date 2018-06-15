package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Позиция на плоскости. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.06.2018
 */
public class PositionTest {

    @Test
    public void whenNextPositionInDirectionThenReturnNextPosition() {
        Position position = new Position(0, 0);
        Position result = position.nextPositionInDirection(DirectionOfMove.DOWN);
        Position expected = new Position(0, -1);
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetArrElThenReturnEl() {
        Object[][] objects = new Object[4][4];
        Object expected = new Object();
        objects[3][2] = expected;
        Position position = new Position(3, 2);
        Object result = position.getArrElement(objects);
        assertThat(result, is(expected));
    }

    @Test
    public void whenPositionPassableThenIsPassableReturnTrue() {
        Position position = new Position(1, 1);
        boolean result = position.isPassable(
                allPassablePositionsWithCheckCoord(1, 1)
        );
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenDirectionPassableThenIsPassableDirectionReturnTrue() {
        Position position = new Position(4, 6);
        boolean result = position.isPassableDirection(
                DirectionOfMove.UP,
                allPassablePositionsWithCheckCoord(4, 7)
        );
        boolean expected = true;
        assertThat(result, is(expected));
    }

    private PassablePositions allPassablePositionsWithCheckCoord(
            int xCoord, int yCoord) {
        return new PassablePositions() {
            @Override
            public boolean isPassableCoordinate(int x, int y) {
                assertThat(xCoord, is(x));
                assertThat(yCoord, is(y));
                return true;
            }

            @Override
            public Collection<Position> allPassablePositions() {
                return null;
            }

            @Override
            public Collection<Position> impassablePositions() {
                return null;
            }
        };
    }
}