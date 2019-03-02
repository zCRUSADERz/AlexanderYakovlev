package ru.job4j.coordinates;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * AroundCoordinates.
 * Координаты ячеек окружающих другую ячейку.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class AroundCoordinates {
    private final BoardCoordinates boardCoordinates;

    public AroundCoordinates(final BoardCoordinates boardCoordinates) {
        this.boardCoordinates = boardCoordinates;
    }

    /**
     * @param coordinate координаты ячейки для которой
     *                   нам нужны окружающие координаты ячеек.
     * @return координаты ячеек окружающих ячейку с переданными координатами
     */
    public final Stream<Coordinate> coordinates(final Coordinate coordinate) {
        return IntStream.iterate(coordinate.x() - 1, x -> x + 1)
                .limit(3)
                .mapToObj(x -> IntStream.iterate(coordinate.y() - 1, y -> y + 1)
                        .limit(3)
                        .filter(y -> this.boardCoordinates.onBoard(x, y))
                        .filter(y -> !(x == coordinate.x() && y == coordinate.y()))
                        .mapToObj(y -> new Coordinate(x, y))
                ).flatMap(coordinateStream -> coordinateStream);
    }
}
