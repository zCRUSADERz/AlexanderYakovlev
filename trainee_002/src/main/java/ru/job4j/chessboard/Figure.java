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
    Figure(Cell position) {
        this.position = position;
    }

    /**
     * Possible figure path.
     * @param source - figure location.
     * @param dest - destination.
     * @return - array of cells along the path of the figure.
     * @throws ImposibleMoveException - if this way impossible.
     */
    public abstract Cell[] way(Cell source, Cell dest) throws ImposibleMoveException;

    /**
     * Copying figure.
     * @param dest - destination.
     * @return - copy of the figure.
     */
    public abstract Figure copy(Cell dest);
}
