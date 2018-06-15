package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Игровая доска.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.06.2018
 */
public class SimpleGameBoard implements GameBoard {
    private final ReentrantLock[][] cells;
    
    public SimpleGameBoard(final ReentrantLock[][] cells) {
        this.cells = cells;
    }

    /**
     * Перейти в новое местоположение на игровой доске.
     * @param current текущее местоположение.
     * @param target желаемое местоположение.
     * @param timeout длительность попытки.
     * @param unit единица измерения времени.
     * @return true, если перейти удалось.
     * @throws InterruptedException если поток был прерван.
     */
    public boolean move(Position current, Position target, long timeout,
                        TimeUnit unit) throws InterruptedException {
        boolean result = false;
        if (target.getArrElement(this.cells).tryLock(timeout, unit)) {
            current.getArrElement(this.cells).unlock();
            result = true;
        }
        return result;
    }

    /**
     * Занять местоположение.
     * @param position занимаемое местоположение.
     */
    public void occupyCell(Position position) {
        position.getArrElement(cells).lock();
    }
}
