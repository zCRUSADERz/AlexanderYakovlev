package ru.job4j.coordinates;

import ru.job4j.cells.CellTypes;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class AroundCoordinates {
    private final BoardCoordinates boardCoordinates;

    public AroundCoordinates(final CellTypes[][] cells) {
        this(new BoardCoordinates(cells));
    }

    public AroundCoordinates(final BoardCoordinates boardCoordinates) {
        this.boardCoordinates = boardCoordinates;
    }

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
