package ru.job4j.chessboard;

/**
 * Chess figure.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public abstract class Figure {
    /**
     * Cell position in chessboard.
     */
    final Cell position;

    /**
     * @param position - position in chessboard.
     */
    public Figure(Cell position) {
        this.position = position;
    }

    public abstract Cell[] way(Cell source, Cell dest) throws ImposibleMoveException;

    public abstract Figure copy(Cell dest);
}
