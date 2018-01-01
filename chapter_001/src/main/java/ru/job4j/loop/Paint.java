package ru.job4j.loop;

/**
 * Paint.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class Paint {

    public String pyramid(int height) {
        StringBuilder pyramid = new StringBuilder();
        String ln = System.lineSeparator();
        int weight = 2 * height - 1;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < weight; column++) {
                if (row >= (height - column - 1) && (row + height - 1) >= column) {
                    pyramid.append("^");
                } else {
                    pyramid.append(" ");
                }
            }
            pyramid.append(ln);
        }
        return pyramid.toString();
    }
}
