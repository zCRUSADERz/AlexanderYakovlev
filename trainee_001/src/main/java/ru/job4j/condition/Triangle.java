package ru.job4j.condition;

/**
 * Triangle.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 31.12.2017
 * @version 1.0
 */
public class Triangle {
    private Point a;
    private Point b;
    private Point c;

    Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Semiperimeter.
     * @param ab - distance between points a and b.
     * @param ac - distance between points a and c.
     * @param bc - distance between points b and c.
     * @return - semiperimeter.
     */
    private double semiperimeter(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }

    /**
     * Area of triangle.
     * @return - area.
     */
    public double area() {
        double rsl = -1;
        double ab = this.a.distanceTo(this.b);
        double ac = this.a.distanceTo(this.c);
        double bc = this.b.distanceTo(this.c);
        if (this.exist(ab, ac, bc)) {
            double p = this.semiperimeter(ab, ac, bc);
            rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
        }
        return rsl;
    }

    /**
     * Existence of a triangle.
     * @param ab - distance between points a and b.
     * @param ac - distance between points a and c.
     * @param bc - distance between points b and c.
     * @return - true if exist.
     */
    private boolean exist(double ab, double ac, double bc) {
        return ((ab + ac) > bc) && ((ab + bc) > ac) && ((ac + bc) > ab);
    }
}
