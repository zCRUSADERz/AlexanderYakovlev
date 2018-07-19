package ru.job4j.xml.xslt.jdbc;

/**
 * Ошибка БД.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
public class DBException extends Exception {

    public DBException(String msg, Throwable e) {
        super(msg, e);
    }
}
