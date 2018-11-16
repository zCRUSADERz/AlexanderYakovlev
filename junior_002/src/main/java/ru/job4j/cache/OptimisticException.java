package ru.job4j.cache;

/**
 * OptimisticException.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.04.2018
 */
public class OptimisticException extends RuntimeException {

    public OptimisticException(String msg) {
        super(msg);
    }
}
