package ru.job4j.coordinates;

import ru.job4j.cells.CellType;

import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public final boolean onBoard(final int x, final int y) {
        return  x >= 0 && x < this.width
                && y >= 0 && y < this.height;
    }

    public final Stream<Coordinate> coordinates() {
        return IntStream.iterate(0, x -> x + 1)
                .limit(this.width)
                .mapToObj(x -> IntStream.iterate(0, y -> y + 1)
                        .limit(this.height)
                        .mapToObj(y -> new Coordinate(x, y)))
                .flatMap(coordinateStream -> coordinateStream);
    }
}
