package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.AroundCoordinates;
import ru.job4j.coordinates.Coordinate;
import ru.job4j.coordinates.UnopenedCells;

import javax.swing.*;
import java.awt.*;

public final class Empty {

    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final AroundCoordinates aroundCoordinates;
        private final Board board;
        private final UnopenedCells unopenedCells;

        public Opening(final Coordinate coordinate, final Board board,
                       final CellTypes[][] cells) {
            this(
                    coordinate,
                    new AroundCoordinates(cells),
                    board,
                    new UnopenedCells(cells)
            );
        }

        public Opening(final Coordinate coordinate,
                       final AroundCoordinates aroundCoordinates,
                       final Board board, final UnopenedCells unopenedCells) {
            this.coordinate = coordinate;
            this.aroundCoordinates = aroundCoordinates;
            this.board = board;
            this.unopenedCells = unopenedCells;
        }

        @Override
        public final void open() {
            this.unopenedCells.coordinates(
                    this.aroundCoordinates.coordinates(this.coordinate)
            ).forEach(
                    this.board::open
            );
        }
    }

    public final static class ImageCell implements CellImage {

        @Override
        public final Image image() {
            return new ImageIcon(
                    getClass().getResource("/img/zero.png")
            ).getImage();
        }
    }
}
