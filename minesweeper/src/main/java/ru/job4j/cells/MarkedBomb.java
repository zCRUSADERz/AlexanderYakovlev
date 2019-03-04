package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

/**
 * MarkedBomb.
 * Бомба помеченная флажком.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class MarkedBomb implements UnopenedCell {
    private final Coordinate coordinate;
    private final Board board;

    public MarkedBomb(final Coordinate coordinate, final Board board) {
        this.coordinate = coordinate;
        this.board = board;
    }

    /**
     * Снимает флажок с ячейки.
     */
    @Override
    public final void mark() {
        this.board.replace(this.coordinate, CellType.UN_OPENED_BOMB);
    }
}
