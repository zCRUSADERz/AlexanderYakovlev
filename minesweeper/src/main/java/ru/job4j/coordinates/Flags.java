package ru.job4j.coordinates;

import ru.job4j.cells.CellType;

import java.util.stream.Stream;

/**
 * Flags.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public class Flags {
    private final CellType[][] cells;

    public Flags(final CellType[][] cells) {
        this.cells = cells;
    }

    public final Stream<Coordinate> coordinates(
            final Stream<Coordinate> coordinates) {
        return coordinates.filter(coordinate -> this.isFlag(
                this.cells[coordinate.x()][coordinate.y()]
        ));
    }

    /**
     * Определяет количество флажков расположенных по этим координатам.
     * @param coordinates координаты ячеек.
     * @return количество флажков.
     */
    public final int count(final Stream<Coordinate> coordinates) {
        return (int) this.coordinates(coordinates).count();
    }

    /**
     * @return общее количество флажков.
     */
    public final int count() {
        int result = 0;
        for (CellType[] cellTypes : this.cells) {
            for (CellType type : cellTypes) {
                if (this.isFlag(type)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean isFlag(final CellType type) {
        return type.equals(CellType.FLAG)
                || type.equals(CellType.BOMB_WITH_FLAG);
    }
}
