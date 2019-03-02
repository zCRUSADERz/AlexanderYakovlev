package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.*;

import java.awt.*;
import java.util.Map;

/**
 * Danger.
 * Ячейка вокруг которой есть одна или более бомб.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Danger {

    /**
     * Opening.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 2.03.2019
     */
    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final AroundCoordinates aroundCoordinates;
        private final Board board;
        private final Flags flags;
        private final Bombs bombs;
        private final UnopenedCells unopenedCells;

        /**
         * Main constructor.
         */
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

        /**
         * Открыть ячейку.
         * В сапере клик на ячейку с числом бомб означает требование
         * открыть все закрытые, не помеченные флажком, ячейки вокруг.
         * Ячейки будут открыты если соблюдено уловие, что число бомб
         * равно числу флажков вокруг.
         */
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
        private final Map<String, Image> images;

        public ImageCell(final Coordinate coordinate,
                         final AroundCoordinates aroundCoordinates,
                         final Bombs bombs, final Map<String, Image> images) {
            this.coordinate = coordinate;
            this.aroundCoordinates = aroundCoordinates;
            this.bombs = bombs;
            this.images = images;
        }

        /**
         * Вернет картинку ячейки.
         * @return картинка ячейки.
         */
        @Override
        public Image image() {
            final int bombCount = this.bombs.count(
                    this.aroundCoordinates.coordinates(this.coordinate)
            );
            return this.images.get(String.format("num%s", bombCount));
        }
    }
}
