package ru.job4j.chessboard;

/**
 * Impossible move Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
class ImposibleMoveException extends Exception {

    ImposibleMoveException(String msg) {
        super(msg);
    }
}
