package ru.job4j;

/**
 * Stop game.
 * Стоп игра.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface StopGame {

    /**
     * Остановить игру.
     */
    void stopGame();

    /**
     * Возвращает булево значение закончена игра или нет.
     * @return закончена игра или нет.
     */
    boolean gameIsStopped();
}
