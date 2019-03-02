package ru.job4j.cells;

import ru.job4j.Board;
import ru.job4j.coordinates.Coordinate;

import java.util.concurrent.atomic.AtomicBoolean;

public final class UnopenedBomb {

    public final static class Opening implements OpeningCell {
        private final Coordinate coordinate;
        private final Board board;
        private final AtomicBoolean gameOver;

        public Opening(final Coordinate coordinate, final Board board,
                       final AtomicBoolean gameOver) {
            this.coordinate = coordinate;
            this.board = board;
            this.gameOver = gameOver;
        }

        @Override
        public final void open() {
            this.board.replace(this.coordinate, CellType.EXPLODED_BOMB);
            this.gameOver.set(true);
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

        @Override
        public void check() {
            this.board.replace(this.coordinate, CellType.BOMB);
        }
    }
}
