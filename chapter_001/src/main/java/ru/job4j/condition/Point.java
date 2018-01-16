package ru.job4j.condition;

/**
 * Point.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.12.2017
 * @version 1.0
 */
public class Point {
    private int x;
    private int y;

    /**
     * Constructor
     * @param x - x coordinate.
     * @param y - y coordinate.
     */
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate distance to point.
     * @param that - Point to wich calculate distance.
     * @return distance.
     */
    public double distanceTo(Point that) {
        return Math.sqrt(
                Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2)
        );
    }
}
