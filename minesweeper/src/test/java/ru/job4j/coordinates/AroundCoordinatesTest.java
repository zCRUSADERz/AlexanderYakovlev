package ru.job4j.coordinates;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AroundCoordinatesTest {

    @Test
    public void whenAllAroundCoordinatesNotOnBoardThenReturnEmptyStream() {
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(0, 0)
        );
        final List<Coordinate> result = aroundCoordinates
                .coordinates(new Coordinate(0, 0))
                .collect(Collectors.toList());
        assertThat(
                result,
                is(Collections.emptyList())
        );
    }

    @Test
    public void sentCoordinateNotExistInResult() {
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(3, 3)
        );
        final List<Coordinate> result = aroundCoordinates
                .coordinates(new Coordinate(1, 1))
                .collect(Collectors.toList());
        assertThat(
                result,
                is(Arrays.asList(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1),
                        new Coordinate(0, 2),
                        new Coordinate(1, 0),
                        new Coordinate(1, 2),
                        new Coordinate(2, 0),
                        new Coordinate(2, 1),
                        new Coordinate(2, 2)
                ))
        );
    }
    
    @Test
    public void coordinatesNotExistOnBoardNotInResult() {
        final AroundCoordinates aroundCoordinates = new AroundCoordinates(
                new BoardCoordinates(2, 3)
        );
        final List<Coordinate> result = aroundCoordinates
                .coordinates(new Coordinate(1, 1))
                .collect(Collectors.toList());
        assertThat(
                result,
                is(Arrays.asList(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1),
                        new Coordinate(0, 2),
                        new Coordinate(1, 0),
                        new Coordinate(1, 2)
                ))
        );
    }
}