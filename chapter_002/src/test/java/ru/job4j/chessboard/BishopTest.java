package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Bishop test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class BishopTest {

    @Test(expected = ImposibleMoveException.class)
    public void whenBishopGoToImpossibleWayThenThrowImpossibleMoveException() throws ImposibleMoveException {
        Bishop bishop = new Bishop(new Cell(5, 4));
        bishop.way(new Cell(5, 4), new Cell(3, 4));
    }

    @Test
    public void whenBishopGoToLeftDownWay() throws ImposibleMoveException {
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 6);
        Bishop bishop = new Bishop(source);
        Cell[] result = bishop.way(source, dest);
        Cell[] expected = new Cell[]{new Cell(6, 5), dest};
        assertThat(result, is(expected));
    }

    @Test
    public void whenBishopGoToLeftUpWay() throws ImposibleMoveException {
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(3, 6);
        Bishop bishop = new Bishop(source);
        Cell[] result = bishop.way(source, dest);
        Cell[] expected = new Cell[]{new Cell(4, 5), dest};
        assertThat(result, is(expected));
    }

    @Test
    public void whenBishopGoToRightUpWay() throws ImposibleMoveException {
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(3, 2);
        Bishop bishop = new Bishop(source);
        Cell[] result = bishop.way(source, dest);
        Cell[] expected = new Cell[]{new Cell(4, 3), dest};
        assertThat(result, is(expected));
    }

    @Test
    public void whenBishopGoToRightDownWay() throws ImposibleMoveException {
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 2);
        Bishop bishop = new Bishop(source);
        Cell[] result = bishop.way(source, dest);
        Cell[] expected = new Cell[]{new Cell(6, 3), dest};
        assertThat(result, is(expected));
    }
}