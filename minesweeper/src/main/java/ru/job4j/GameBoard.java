package ru.job4j;

import ru.job4j.cells.CheckedCell;
import ru.job4j.cells.OpeningCell;
import ru.job4j.cells.CellTypes;
import ru.job4j.cells.UnopenedCell;
import ru.job4j.coordinates.Coordinate;

public final class GameBoard implements Board {
    private final CellTypes[][] cells;
    private final Cells<OpeningCell> openingCells;
    private final Cells<UnopenedCell> markingCells;
    private final Cells<CheckedCell> checkedCells;

    public GameBoard(final CellTypes[][] cells,
                     final Cells<OpeningCell> openingCells,
                     final Cells<UnopenedCell> markingCells,
                     final Cells<CheckedCell> checkedCells) {
        this.cells = cells;
        this.openingCells = openingCells;
        this.markingCells = markingCells;
        this.checkedCells = checkedCells;
    }

    @Override
    public final void open(final Coordinate coordinate) {
        this.openingCells.cell(this, coordinate).open();
    }

    @Override
    public final void mark(final Coordinate coordinate) {
        this.markingCells.cell(this, coordinate).mark();
    }

    @Override
    public final void check(final Coordinate coordinate) {
        this.checkedCells.cell(this, coordinate).check();
    }

    @Override
    public void replace(final Coordinate coordinate, final CellTypes type) {
        this.cells[coordinate.x()][coordinate.y()] = type;
    }
}
