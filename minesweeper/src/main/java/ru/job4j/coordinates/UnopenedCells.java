package ru.job4j.coordinates;

import ru.job4j.cells.CellTypes;

import java.util.stream.Stream;

public final class UnopenedCells {
    private final CellTypes[][] cells;

    public UnopenedCells(final CellTypes[][] cells) {
        this.cells = cells;
    }

    public final Stream<Coordinate> coordinates(
            final Stream<Coordinate> coordinates) {
        return coordinates.filter(coordinate -> this.isUnopened(
                this.cells[coordinate.x()][coordinate.y()]
        ));
    }

    public final int count(final Stream<Coordinate> coordinates) {
        return (int) this.coordinates(coordinates).count();
    }

    public final int count() {
        int result = 0;
        for (CellTypes[] cellTypes : this.cells) {
            for (CellTypes type : cellTypes) {
                if (this.isUnopened(type)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean isUnopened(final CellTypes type) {
        return type.equals(CellTypes.UN_OPENED)
                || type.equals(CellTypes.UN_OPENED_BOMB);
    }
}
