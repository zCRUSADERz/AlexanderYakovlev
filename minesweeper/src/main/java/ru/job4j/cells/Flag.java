package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

/**
 * Empty.
 * Флажок.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Flag implements UnopenedCell, CheckedCell {
    private final Coordinate coordinate;
    private final Board board;

    public Flag(final Coordinate coordinate, final Board board) {
        this.coordinate = coordinate;
        this.board = board;
    }

    /**
     * Снять флажок с ячейки.
     */
    @Override
    public final void mark() {
        this.board.replace(this.coordinate, CellType.UN_OPENED);
    }

    /**
     * Проверить правильно ли установлен флажок.
     */
    @Override
    public final void check() {
        this.board.replace(this.coordinate, CellType.NO_BOMB);
    }
}
