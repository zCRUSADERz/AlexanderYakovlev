package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

public final class MarkedBomb implements UnopenedCell, CheckedCell {
    private final Coordinate coordinate;
    private final Board board;

    public MarkedBomb(final Coordinate coordinate, final Board board) {
        this.coordinate = coordinate;
        this.board = board;
    }

    @Override
    public final void mark() {
        this.board.replace(this.coordinate, CellType.UN_OPENED_BOMB);
    }

    @Override
    public void check() {
        this.board.replace(this.coordinate, CellType.BOMB);
    }
}
