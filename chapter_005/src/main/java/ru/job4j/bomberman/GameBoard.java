package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;

/**
 * Игровая доска.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.06.2018
 */
public interface GameBoard {

    /**
     * Перейти в новое местоположение на игровой доске.
     * @param current текущее местоположение.
     * @param target желаемое местоположение.
     * @param timeout длительность попытки.
     * @param unit единица измерения времени.
     * @return true, если перейти удалось.
     * @throws InterruptedException если поток был прерван.
     */
    boolean move(Position current, Position target, long timeout,
                 TimeUnit unit) throws InterruptedException;

    /**
     * Занять местоположение.
     * @param position занимаемое местоположение.
     */
    void occupyCell(Position position);
}
