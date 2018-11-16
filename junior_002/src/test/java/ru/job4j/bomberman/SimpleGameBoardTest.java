package ru.job4j.bomberman;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Игровая доска. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class SimpleGameBoardTest {
    private final int boardSize = 2;
    private final ReentrantLock[][] locks = new ReentrantLock[boardSize][boardSize];
    private final GameBoard board = new SimpleGameBoard(locks);

    @Before
    public void initLocks() {
        for (int x = 0; x < this.boardSize; x++) {
            for (int y = 0; y < this.boardSize; y++) {
                this.locks[x][y] = new ReentrantLock();
            }
        }
    }

    @Test
    public void whenMoveOnFreePositionThenReturnTrue() throws InterruptedException {
        this.locks[0][0].lock();
        boolean expected = true;
        boolean result = this.board.move(
                new Position(0, 0),
                new Position(0, 1),
                1,
                TimeUnit.MILLISECONDS);
        assertThat(result, is(expected));
    }

    @Test
    public void whenMoveThenNewPositionLocked() throws InterruptedException {
        this.locks[0][0].lock();
        this.board.move(
                new Position(0, 0),
                new Position(0, 1),
                1,
                TimeUnit.MILLISECONDS);
        boolean expected = true;
        boolean result = this.locks[0][1].isLocked();
        assertThat(result, is(expected));
    }

    @Test
    public void whenMoveThenLastPositionUnLocked() throws InterruptedException {
        this.locks[0][0].lock();
        this.board.move(
                new Position(0, 0),
                new Position(0, 1),
                1,
                TimeUnit.MILLISECONDS);
        boolean expected = false;
        boolean result = this.locks[0][0].isLocked();
        assertThat(result, is(expected));
    }

    @Test
    public void whenCellOccupiedThenMoveReturnFalse() throws InterruptedException {
        Thread thread = new Thread(() -> locks[1][1].lock());
        thread.start();
        thread.join();
        this.locks[1][0].lock();
        boolean result = this.board.move(
                new Position(1, 0),
                new Position(1, 1),
                1,
                TimeUnit.MILLISECONDS);
        boolean expected = false;
        assertThat(result, is(expected));
    }

    @Test
    public void whenOccupyCellThenCellLocked() {
        this.board.occupyCell(new Position(0, 0));
        boolean result = locks[0][0].isLocked();
        boolean expected = true;
        assertThat(result, is(expected));
    }
}