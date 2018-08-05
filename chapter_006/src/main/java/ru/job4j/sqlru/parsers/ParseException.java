package ru.job4j.sqlru.parsers;

/**
 * Parser error.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class ParseException extends Exception {

    public ParseException(String msg, Throwable e) {
        super(msg, e);
    }
}
