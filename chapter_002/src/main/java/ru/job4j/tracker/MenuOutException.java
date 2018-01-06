package ru.job4j.tracker;

/**
 * Menu out Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.01.2017
 * @version 1.0
 */
public class MenuOutException extends RuntimeException {

    /**
     * Constructor.
     * @param msg - error message.
     */
    public MenuOutException(String msg) {
        super(msg);
    }
}
