package ru.job4j.chessboard;

/**
 * Figure not found Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
class FigureNotFoundException extends Exception {

    FigureNotFoundException(String msg) {
        super(msg);
    }
}
