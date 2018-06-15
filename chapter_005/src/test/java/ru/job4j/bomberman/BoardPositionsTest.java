package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Позиции на игровой доске. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class BoardPositionsTest {
    private final int boardSize = 10;
    private BoardPositions positions = new BoardPositions(boardSize);

    @Test
    public void whenTwoCoordAreNegativeThenIsCoordinateOnBoardReturnFalse() {
        int x = -1;
        int y = -1;
        boolean expected = false;
        boolean result = this.positions.isCoordinateOnBoard(x, y);
        assertThat(result, is(expected));
    }

    @Test
    public void whenOneCoordAreNegativeThenIsCoordinateOnBoardReturnFalse() {
        int x = -1;
        int y = 0;
        boolean expected = false;
        boolean result = this.positions.isCoordinateOnBoard(x, y);
        assertThat(result, is(expected));
        result = this.positions.isCoordinateOnBoard(y, x);
        assertThat(result, is(expected));
    }

    @Test
    public void whenCoordOnBoardThenIsCoordinateOnBoardReturnTrue() {
        int x = 0;
        int y = 0;
        boolean expected = true;
        boolean result = this.positions.isCoordinateOnBoard(x, y);
        assertThat(result, is(expected));
    }

    @Test
    public void whenOneCoordMoreThanBoardSizeThenIsCoordinateOnBoardReturnFalse() {
        int x = boardSize + 1;
        int y = 0;
        boolean expected = false;
        boolean result = this.positions.isCoordinateOnBoard(x, y);
        assertThat(result, is(expected));
        result = this.positions.isCoordinateOnBoard(y, x);
        assertThat(result, is(expected));
    }

    @Test
    public void whenTwoCoordMoreThanBoardSizeThenIsCoordinateOnBoardReturnFalse() {
        int x = this.boardSize + 1;
        int y = this.boardSize + 1;
        boolean expected = false;
        boolean result = this.positions.isCoordinateOnBoard(x, y);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAllBoardPositionThenReturnAllBoardPosition() {
        this.positions = new BoardPositions(2);
        Collection<Position> expected = new HashSet<>();
        expected.add(new Position(0, 0));
        expected.add(new Position(0, 1));
        expected.add(new Position(1, 0));
        expected.add(new Position(1, 1));
        Collection<Position> result = this.positions.allBoardPositions();
        assertThat(result, is(expected));
    }

    @Test
    public void whenNewBoardPositionsWithAllPositionsThenReturnSameAllBoardPosition() {
        HashSet<Position> expected = new HashSet<>();
        expected.add(new Position(0, 0));
        this.positions = new BoardPositions(1, expected);
        Collection<Position> result = this.positions.allBoardPositions();
        assertThat(result, is(expected));
    }
}