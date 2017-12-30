package ru.job4j.calculator;

/**
 * Calculator
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.12.2017
 * @version 1.0
 */
public class Calculator {
    private double result;

    /**
     * First add second
     * @param first - first num
     * @param second - second num
     */
    void add(double first, double second) {
        result = first + second;
    }

    /**
     * First minus second
     * @param first - first num
     * @param second - second num
     */
    void subtract(double first, double second) {
        result = first - second;
    }

    /**
     * First divide second
     * @param first - first num
     * @param second - second num
     */
    void div(double first, double second) {
        result = first / second;
    }

    /**
     * First multiply second
     * @param first - first num
     * @param second - second num
     */
    void multiple(double first, double second) {
        result = first * second;
    }

    /**
     * @return result;
     */
    double getResult() {
        return result;
    }
}