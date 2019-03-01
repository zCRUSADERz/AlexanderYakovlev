package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

import javax.swing.*;
import java.awt.*;

public final class Flag {

    public final static class Marked implements UnopenedCell {
        private final Coordinate coordinate;
        private final Board board;

        public Marked(final Coordinate coordinate, final Board board) {
            this.coordinate = coordinate;
            this.board = board;
        }

        @Override
        public final void mark() {
            this.board.replace(this.coordinate, CellTypes.UN_OPENED);
        }
    }

    public final static class ImageCell implements CellImage {

        @Override
        public final Image image() {
            return new ImageIcon(
                    getClass().getResource("/img/flaged.png")
            ).getImage();
        }
    }

    public final static class Checked implements CheckedCell {
        private final Coordinate coordinate;
        private final Board board;

        public Checked(final Coordinate coordinate, final Board board) {
            this.coordinate = coordinate;
            this.board = board;
        }

        @Override
        public final void check() {
            this.board.replace(this.coordinate, CellTypes.NO_BOMB);
        }
    }
}
