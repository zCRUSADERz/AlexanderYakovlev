package ru.job4j.pool;

import java.util.List;

/**
 * Работа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.04.2018
 */
public class SimpleWork implements Work {
    private final List<Integer> result;

    public SimpleWork(List<Integer> result) {
        this.result = result;
    }

    /**
     * Выполнить работу.
     */
    public void work() {
        int workResult = 0;
        for (int i = 0; i < 100; i++) {
            workResult += i;
        }
        result.add(workResult);
    }
}
