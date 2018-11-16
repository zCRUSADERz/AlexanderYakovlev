package ru.job4j;

/**
 * MathUtil.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.11.2018
 */
public class MathUtil {

    public static double add(int left, int second) {
        return left + second;
    }

    public static double div(int left, int second) {
        return left / second;
    }

    public static double linearFunc(double x) {
        return 2 * x;
    }

    public static double quadraticFunc(double x) {
        return x * x;
    }
}
