package ru.job4j;

import ru.job4j.cells.CheckedCell;
import ru.job4j.cells.OpeningCell;
import ru.job4j.cells.CellType;
import ru.job4j.cells.UnopenedCell;
import ru.job4j.coordinates.Coordinate;

/**
 * GameBoard.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2019
 */
public final class GameBoard implements Board {
    private final CellType[][] cells;
    private final CellsFactory<OpeningCell> openingCellsFactory;
    private final CellsFactory<UnopenedCell> markingCellsFactory;
    private final CellsFactory<CheckedCell> checkedCellsFactory;

    public GameBoard(final CellType[][] cells,
                     final CellsFactory<OpeningCell> openingCellsFactory,
                     final CellsFactory<UnopenedCell> markingCellsFactory,
                     final CellsFactory<CheckedCell> checkedCellsFactory) {
        this.cells = cells;
        this.openingCellsFactory = openingCellsFactory;
        this.markingCellsFactory = markingCellsFactory;
        this.checkedCellsFactory = checkedCellsFactory;
    }

    /**
     * Откроет ячейку по переданным координатам.
     * @param coordinate координаты ячейки, которую необходимо открыть.
     */
    @Override
    public final void open(final Coordinate coordinate) {
        this.openingCellsFactory.cell(this, coordinate).open();
    }

    /**
     * Поставит флажок(или уберет существующий) в ячейке по переданным координатам.
     * @param coordinate координаты ячейки, которую необходимо пометить флажком.
     */
    @Override
    public final void mark(final Coordinate coordinate) {
        this.markingCellsFactory.cell(this, coordinate).mark();
    }

    /**
     * Проверит правильность установки флажка если он установлен.
     * @param coordinate координаты ячейки, которую необходимо проверить.
     */
    @Override
    public final void check(final Coordinate coordinate) {
        this.checkedCellsFactory.cell(this, coordinate).check();
    }

    /**
     * Заменить состояние ячейки по переданным координатам на новое.
     * @param coordinate координаты ячейки, которую необходимо изменить.
     * @param type тип ячейки, на который будет заменен прошлый.
     */
    @Override
    public void replace(final Coordinate coordinate, final CellType type) {
        this.cells[coordinate.x()][coordinate.y()] = type;
    }
}
