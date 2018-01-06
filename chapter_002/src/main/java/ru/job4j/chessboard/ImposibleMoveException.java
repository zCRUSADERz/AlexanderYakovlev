package ru.job4j.chessboard;

/**
 * Impossible move Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class ImposibleMoveException extends Exception {

    public ImposibleMoveException(String msg) {
        super(msg);
    }
}
