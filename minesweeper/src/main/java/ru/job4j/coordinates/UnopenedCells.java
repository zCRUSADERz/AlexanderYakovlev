package ru.job4j.coordinates;

import ru.job4j.cells.CellType;

import java.util.stream.Stream;

public final class UnopenedCells {
    private final CellType[][] cells;

    public UnopenedCells(final CellType[][] cells) {
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
        for (CellType[] cellTypes : this.cells) {
            for (CellType type : cellTypes) {
                if (this.isUnopened(type)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean isUnopened(final CellType type) {
        return type.equals(CellType.UN_OPENED)
                || type.equals(CellType.UN_OPENED_BOMB);
    }
}
