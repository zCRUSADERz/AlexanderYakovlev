package ru.job4j.loop;

/**
 * Factorial.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class Factorial {

    /**
     * Calculates the factorial of number n.
     * @param n - number.
     * @return - factorial n.
     */
    public int calc(int n) {
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
