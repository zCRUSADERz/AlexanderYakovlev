package ru.job4j;

/**
 * Stop game.
 * Стоп игра.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class StopGameSimple implements StopGame {
    private boolean stopped = false;

    /**
     * Остановить игру.
     */
    @Override
    public void stopGame() {
        this.stopped = true;
    }

    /**
     * Возвращает булево значение закончена игра или нет.
     * @return закончена игра или нет.
     */
    @Override
    public boolean gameIsStopped() {
        return this.stopped;
    }
}
