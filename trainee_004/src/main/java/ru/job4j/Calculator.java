package ru.job4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Calculator.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.11.2018
 */
public class Calculator {

    public void multiple(int start, int finish, int value,
                         BiFunction<Integer, Integer, Double> op,
                         Consumer<Double> media) {
        for (int index = start; index != finish; index++) {
            media.accept(op.apply(value, index));
        }
    }

    public List<Double> diapason(int start, int end, Function<Double, Double> func) {
        final List<Double> result = new ArrayList<>();
        for (int index = start; index != end; index++) {
            result.add(func.apply((double) index));
        }
        return result;
    }
}
