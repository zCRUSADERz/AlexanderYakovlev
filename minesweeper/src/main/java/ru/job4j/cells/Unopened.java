package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.AroundCoordinates;
import ru.job4j.coordinates.Bombs;
import ru.job4j.coordinates.Coordinate;

import javax.swing.*;
import java.awt.*;

public final class Unopened {

    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final AroundCoordinates aroundCoordinates;
        private final Board board;
        private final Bombs bombs;

        public Opening(final Coordinate coordinate, final CellTypes[][] cells,
                       final Board board) {
            this(
                    coordinate,
                    new AroundCoordinates(cells),
                    board,
                    new Bombs(cells)
            );
        }

        public Opening(final Coordinate coordinate,
                       final AroundCoordinates aroundCoordinates,
                       final Board board, final Bombs bombs) {
            this.coordinate = coordinate;
            this.aroundCoordinates = aroundCoordinates;
            this.board = board;
            this.bombs = bombs;
        }

        @Override
        public void open() {
            final int bombCount = this.bombs.count(
                    this.aroundCoordinates.coordinates(this.coordinate)
            );
            if (bombCount == 0) {
                this.board.replace(this.coordinate, CellTypes.EMPTY);
                this.board.open(this.coordinate);
            } else {
                this.board.replace(this.coordinate, CellTypes.DANGER);
            }
        }
    }

    public final static class Marked implements UnopenedCell {
        private final Coordinate coordinate;
        private final Board board;

        public Marked(final Coordinate coordinate, final Board board) {
            this.coordinate = coordinate;
            this.board = board;
        }

        @Override
        public final void mark() {
            this.board.replace(this.coordinate, CellTypes.FLAG);
        }
    }

    public final static class ImageCell implements CellImage {

        @Override
        public final Image image() {
            return new ImageIcon(
                    getClass().getResource("/img/closed.png")
            ).getImage();
        }
    }
}
