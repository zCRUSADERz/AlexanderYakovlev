package ru.job4j.chessboard;

/**
 * Figure not found Exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class FigureNotFoundException extends Exception {

    public FigureNotFoundException(String msg) {
        super(msg);
    }
}
