package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.*;

import javax.swing.*;
import java.awt.*;

public final class Danger {

    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final AroundCoordinates aroundCoordinates;
        private final Board board;
        private final Flags flags;
        private final Bombs bombs;
        private final UnopenedCells unopenedCells;

        public Opening(final Coordinate coordinate, final Board board,
                       final CellType[][] cells) {
            this(
                    coordinate,
                    new AroundCoordinates(cells),
                    board,
                    new Flags(cells),
                    new Bombs(cells),
                    new UnopenedCells(cells)
            );
        }

        public Opening(final Coordinate coordinate,
                      final AroundCoordinates aroundCoordinates, final Board board,
                      final Flags flags, final Bombs bombs,
                      final UnopenedCells unopenedCells) {
            this.coordinate = coordinate;
            this.aroundCoordinates = aroundCoordinates;
            this.board = board;
            this.flags = flags;
            this.bombs = bombs;
            this.unopenedCells = unopenedCells;
        }

        @Override
        public final void open() {
            final int bombsCount = this.bombs.count(
                    this.aroundCoordinates.coordinates(this.coordinate)
            );
            final int flagsCount = this.flags.count(
                    this.aroundCoordinates.coordinates(this.coordinate)
            );
            if (bombsCount == flagsCount) {
                this.unopenedCells.coordinates(
                        this.aroundCoordinates.coordinates(this.coordinate)
                ).forEach(board::open);
            }
        }
    }

    public final static class ImageCell implements CellImage {
        private final Coordinate coordinate;
        private final AroundCoordinates aroundCoordinates;
        private final Bombs bombs;

        public ImageCell(final Coordinate coordinate, final CellType[][] cells) {
            this(coordinate, new AroundCoordinates(cells), new Bombs(cells));
        }

        public ImageCell(final Coordinate coordinate,
                         final AroundCoordinates aroundCoordinates,
                         final Bombs bombs) {
            this.coordinate = coordinate;
            this.aroundCoordinates = aroundCoordinates;
            this.bombs = bombs;
        }

        @Override
        public Image image() {
            final int bombCount = this.bombs.count(
                    this.aroundCoordinates.coordinates(this.coordinate)
            );
            return new ImageIcon(
                    getClass().getResource(
                            String.format("/img/num%s.png", bombCount)
                    )
            ).getImage();
        }
    }
}
