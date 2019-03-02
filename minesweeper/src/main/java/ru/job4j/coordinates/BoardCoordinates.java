package ru.job4j.coordinates;

import ru.job4j.cells.CellType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * AroundCoordinates.
 * Координаты ячеек находящихся на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class BoardCoordinates {
    private final int width;
    private final int height;

    public BoardCoordinates(final CellType[][] cells) {
        this(
                cells.length,
                cells[0].length
        );
    }

    public BoardCoordinates(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Определяет находятся ли переданные координаты на игровой доске.
     * @param x значение координаты.
     * @param y значение координаты.
     * @return true, если координаты находятся на игровой доске.
     */
    public final boolean onBoard(final int x, final int y) {
        return  x >= 0 && x < this.width
                && y >= 0 && y < this.height;
    }

    /**
     * Все координаты ячеек игровой доски.
     * @return все координаты ячеек игровой доски
     */
    public final Stream<Coordinate> coordinates() {
        return IntStream.iterate(0, x -> x + 1)
                .limit(this.width)
                .mapToObj(x -> IntStream.iterate(0, y -> y + 1)
                        .limit(this.height)
                        .mapToObj(y -> new Coordinate(x, y)))
                .flatMap(coordinateStream -> coordinateStream);
    }
}
