package ru.job4j.sqlru;

/**
 * Main app exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuException extends Exception {

    public SqlRuException(String msg, Throwable e) {
        super(msg, e);
    }
}
