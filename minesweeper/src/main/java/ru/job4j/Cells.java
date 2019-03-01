package ru.job4j;

import ru.job4j.cells.CellTypes;
import ru.job4j.coordinates.Coordinate;

import java.util.Map;
import java.util.function.BiFunction;

public final class Cells<T> {
    private final CellTypes[][] cells;
    private final Map<CellTypes, BiFunction<Board, Coordinate,  T>> cellFactory;
    private final BiFunction<Board, Coordinate, T> defaultFactory;

    public Cells(
            final CellTypes[][] cells,
            final Map<CellTypes, BiFunction<Board, Coordinate, T>> cellFactory,
            final BiFunction<Board, Coordinate, T> defaultFactory) {
        this.cells = cells;
        this.cellFactory = cellFactory;
        this.defaultFactory = defaultFactory;
    }

    public final T cell(final Board board, final Coordinate coordinate) {
        return this.cellFactory.getOrDefault(
                this.cells[coordinate.x()][coordinate.y()],
                this.defaultFactory
        ).apply(board, coordinate);
    }
}
