package ru.job4j.chessboard;

/**
 * Bishop figure.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class Bishop extends Figure {

    /**
     * @param dest - cell.
     */
    Bishop(Cell dest) {
        super(dest);
    }

    /**
     * Way figure.
     * @param source - source cell.
     * @param dest - destination cell.
     * @return - cell on way figure.
     * @throws ImposibleMoveException - if this way impossible.
     */
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        Cell[] cells;
        boolean first = (source.x - dest.x) == (source.y - dest.y);
        boolean second = (source.x + source.y) == (dest.x + dest.y);
        if (!(first || second)) {
            throw new ImposibleMoveException(String.format("Ход на ячейку %d:%d невозможен.", dest.x, dest.y));
        } else {
            int numCells = dest.x - source.x;
            cells = new Cell[Math.abs(numCells)];
            int nextX = source.x;
            int nextY = source.y;
            for (int i = 0; i < cells.length; i++) {
                nextX += numCells > 0 ? 1 : -1;
                if ((first && (numCells > 0) || (second && (numCells < 0)))) {
                    nextY++;
                } else {
                    nextY--;
                }
                cells[i] = new Cell(nextX, nextY);
            }
        }
        return cells;
    }

    /**
     * Copy figure.
     * @param dest - new cell.
     * @return - new Figure.
     */
    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest);
    }

    /**
     * Equals if position figures equals.
     * @param obj - figure.
     * @return - true if equals.
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            if (position.equals(((Bishop) obj).position)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * HashCode. 4 - TypeFigure.
     * @return - unique HashCode.
     */
    @Override
    public int hashCode() {
        return 4 + position.hashCode();
    }
}
