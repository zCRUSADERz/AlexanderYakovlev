package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

import javax.swing.*;
import java.awt.*;

/**
 * Empty.
 * Флажок.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class Flag {

    public final static class Marked implements UnopenedCell {
        private final Coordinate coordinate;
        private final Board board;

        public Marked(final Coordinate coordinate, final Board board) {
            this.coordinate = coordinate;
            this.board = board;
        }

        /**
         * Снять флажок с ячейки.
         */
        @Override
        public final void mark() {
            this.board.replace(this.coordinate, CellType.UN_OPENED);
        }
    }

    public final static class ImageCell implements CellImage {

        /**
         * Вернет картинку флажка.
         * @return картинка флажка.
         */
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

        /**
         * Проверить правильно ли установлен флажок.
         */
        @Override
        public final void check() {
            this.board.replace(this.coordinate, CellType.NO_BOMB);
        }
    }
}
