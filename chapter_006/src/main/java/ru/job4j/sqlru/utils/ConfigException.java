package ru.job4j.sqlru.utils;

/**
 * App configuration exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class ConfigException extends Exception {

    public ConfigException(String msg, Throwable e) {
        super(msg, e);
    }
}
