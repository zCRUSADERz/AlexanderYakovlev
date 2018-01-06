package ru.job4j.chessboard;

/**
 * Occupied way Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class OccupiedWayException extends Exception {

    public OccupiedWayException(String msg) {
        super(msg);
    }
}
