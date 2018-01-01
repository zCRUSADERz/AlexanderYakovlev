package ru.job4j.loop;

import java.util.function.BiPredicate;

/**
 * Paint pyramid.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class Paint {

    /**
     * Paint right triangle of the pyramid.
     * @param height - height of the pyramid.
     * @return - right triangle of the pyramid.
     */
    public String rightTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= column);
    }

    /**
     * Paint left triangle of the pyramid.
     * @param height - height of the pyramid.
     * @return - left triangle of the pyramid.
     */
    public String leftTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= height - column - 1);
    }

    /**
     * Paint pyramid.
     * @param height - height of the pyramid.
     * @return - pyramid.
     */
    public String pyramid(int height) {
        return this.loopBy(
                height,
                2 * height - 1,
                (row, column) -> row >= height - column - 1 && row + height - 1 >= column);
    }

    private String loopBy(int height, int width, BiPredicate<Integer, Integer> predict) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != width; column++) {
                if (predict.test(row, column)) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
}
