package ru.job4j;

import ru.job4j.cells.CellTypes;
import ru.job4j.coordinates.Coordinate;

public interface Board {
    void open(final Coordinate coordinate);

    void mark(final Coordinate coordinate);

    void check(final Coordinate coordinate);

    void replace(final Coordinate coordinate, final CellTypes type);
}
