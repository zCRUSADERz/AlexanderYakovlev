package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Triangle test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 31.12.2017
 * @version 1.0
 */
public class TriangleTest {

    /**
     * Test Triangle.
     */
    @Test
    public void whenTriangleSetThreePointsThenTriangleArea() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Point c = new Point(2, 0);
        Triangle triangle = new Triangle(a, b, c);
        double result = triangle.area();
        double expected = 2.0;
        assertThat(result, closeTo(expected, 0.1));
    }

    /**
     * Test non-existent triangle.
     */
    @Test
    public void whenSetThreePointsOnOneStraightLineThenTriangleAreaMinusOne() {
        Point a = new Point(2, 2);
        Point b = new Point(10, 10);
        Point c = new Point(0, 0);
        Triangle triangle = new Triangle(a, b, c);
        double result = triangle.area();
        double expected = -1.0;
        assertThat(result, is(expected));
    }
}
