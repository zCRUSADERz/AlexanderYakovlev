package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.AroundCoordinates;
import ru.job4j.coordinates.Coordinate;
import ru.job4j.coordinates.UnopenedCells;

import javax.swing.*;
import java.awt.*;

/**
 * Empty.
 * Пустая ячейка, вокруг которой нет ни одной бомбы.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Empty {

    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final AroundCoordinates aroundCoordinates;
        private final Board board;
        private final UnopenedCells unopenedCells;

        public Opening(final Coordinate coordinate, final Board board,
                       final CellType[][] cells) {
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

        /**
         * Открыть пустую ячейку.
         * Так как вокруг пустой ячейки нет ни одной бомбы,
         * то все окружающие не открытые ячейки будут автоматически открыты.
         */
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

        /**
         * Вернет картинку ячейки.
         * @return картинка ячейки.
         */
        @Override
        public final Image image() {
            return new ImageIcon(
                    getClass().getResource("/img/zero.png")
            ).getImage();
        }
    }
}
