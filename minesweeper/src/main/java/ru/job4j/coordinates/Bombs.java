package ru.job4j.coordinates;

import ru.job4j.cells.CellType;

import java.util.stream.Stream;

/**
 * Bombs.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Bombs {
    private final CellType[][] cells;

    public Bombs(final CellType[][] cells) {
        this.cells = cells;
    }

    public final Stream<Coordinate> coordinates(
            final Stream<Coordinate> coordinates) {
        return coordinates.filter(coordinate -> this.isBomb(
                this.cells[coordinate.x()][coordinate.y()]
        ));
    }

    /**
     * Определяет количество бомб расположенных по этим координатам.
     * @param coordinates координаты ячеек.
     * @return количество бомб.
     */
    public final int count(final Stream<Coordinate> coordinates) {
        return (int) this.coordinates(coordinates).count();
    }

    /**
     * @return общее количество бомб.
     */
    public final int count() {
        int result = 0;
        for (CellType[] cellTypes : this.cells) {
            for (CellType type : cellTypes) {
                if (this.isBomb(type)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean isBomb(final CellType type) {
        return type.equals(CellType.UN_OPENED_BOMB)
                || type.equals(CellType.BOMB_WITH_FLAG)
                || type.equals(CellType.EXPLODED_BOMB)
                || type.equals(CellType.BOMB);
    }
}
