package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * UnopenedBomb.
 * Закрытая ячейка в которой находится бомба.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class UnopenedBomb {

    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final Board board;
        private final AtomicBoolean gameOver;
        private final JButton reset;
        private final ImageIcon dead;

        public Opening(final Coordinate coordinate, final Board board,
                       final AtomicBoolean gameOver, final JButton reset,
                       final ImageIcon dead) {
            this.coordinate = coordinate;
            this.board = board;
            this.gameOver = gameOver;
            this.reset = reset;
            this.dead = dead;
        }

        /**
         * Открыть ячейку.
         * Открытие ячейки с бомбой приводит к ее взрыву и окончанию игры.
         */
        @Override
        public final void open() {
            this.board.replace(this.coordinate, CellType.EXPLODED_BOMB);
            this.gameOver.set(true);
            this.reset.setIcon(this.dead);
        }
    }

    public final static class Marked implements UnopenedCell {
        private final Coordinate coordinate;
        private final Board board;

        public Marked(final Coordinate coordinate, final Board board) {
            this.coordinate = coordinate;
            this.board = board;
        }

        /**
         * Пометить ячейку флажком.
         */
        @Override
        public final void mark() {
            this.board.replace(this.coordinate, CellType.BOMB_WITH_FLAG);
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
         * В ячейке будет открыта бомба без ее взрыва.
         */
        @Override
        public void check() {
            this.board.replace(this.coordinate, CellType.BOMB);
        }
    }
}
