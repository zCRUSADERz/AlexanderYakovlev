package ru.job4j.chessboard;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Board test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class BoardTest {

    @Test
    public void whenFigureMoveEmptyWayThenMoveFigure() throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 6);
        board.figures[source.x][source.y] = new Bishop(source);
        board.move(source, dest);
        Figure result = board.figures[dest.x][dest.y];
        Figure expected = new Bishop(dest);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFigureMoveEmptyWayThenSourceCellEmpty() throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 6);
        board.figures[source.x][source.y] = new Bishop(source);
        board.move(source, dest);
        Figure result = board.figures[source.x][source.y];
        assertThat(result, nullValue());
    }

    @Test(expected = OccupiedWayException.class)
    public void whenFigureMoveOccupiedWayThenThrowOccupiedWayException() throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 6);
        board.figures[source.x][source.y] = new Bishop(source);
        board.figures[6][5] = new Bishop(new Cell(6, 5));
        board.move(source, dest);
    }

    @Test(expected = ImposibleMoveException.class)
    public void whenFigureMoveImposibleWayThenThrowImposibleMoveException() throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 7);
        board.figures[source.x][source.y] = new Bishop(source);
        board.move(source, dest);
    }

    @Test(expected = FigureNotFoundException.class)
    public void whenExistFigureMoveThenThrowImposibleMoveException() throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Board board = new Board();
        Cell source = new Cell(5, 4);
        Cell dest = new Cell(7, 7);
        board.move(source, dest);
    }
}