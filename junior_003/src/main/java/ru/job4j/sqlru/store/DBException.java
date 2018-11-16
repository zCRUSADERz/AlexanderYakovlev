package ru.job4j.sqlru.store;

/**
 * DB error.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class DBException extends Exception {

    public DBException(String msg, Throwable e) {
        super(msg, e);
    }
}
