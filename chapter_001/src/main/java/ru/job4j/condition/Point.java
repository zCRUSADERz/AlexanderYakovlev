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
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate distance from point a to point b
     */
    public static void main(String[] args) {
        Point a = new Point(0, 1);
        Point b = new Point(2, 5);
        System.out.println("x1 = " + a.x);
        System.out.println("y1 = " + a.y);
        System.out.println("x2 = " + b.x);
        System.out.println("y2 = " + b.y);

        double result = a.distanceTo(b);
        System.out.println("Расстояние между точками А и В : " + result);
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
