package ru.job4j.chessboard;

/**
 * Cell.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class Cell {
    public final int x;
    public final int y;

    /**
     * @param x - x coordinate in chessboard.
     * @param y - y coordinate in chessboard.
     */
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Equals if x and y coordinates are equal.
     * @param obj - cell.
     * @return - true if equal.
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            if (x == ((Cell) obj).x && y == ((Cell) obj).y) {
                result = true;
            }
        }
        return result;
    }

    /**
     * HashCode.
     * @return - unique hashCode.
     */
    @Override
    public int hashCode() {
        return x + 8 * y;
    }
}
