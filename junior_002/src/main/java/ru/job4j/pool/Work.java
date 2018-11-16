package ru.job4j.pool;

/**
 * Работа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.04.2018
 */
public interface Work {
    void work() throws InterruptedException;
}
