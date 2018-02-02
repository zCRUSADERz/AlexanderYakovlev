package ru.job4j.tracker;

/**
 * Menu out Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.01.2017
 * @version 1.0
 */
class MenuOutException extends RuntimeException {

    /**
     * Constructor.
     * @param msg - error message.
     */
    MenuOutException(String msg) {
        super(msg);
    }
}
