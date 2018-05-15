package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Игровая доска.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class SimpleGameBoard implements GameBoard {
    private final int size;
    private final ReentrantLock[][] board;
    private final LocationOfHeroes locations;

    public SimpleGameBoard(int size, LocationOfHeroes locations) {
        this.size = size;
        this.board = new ReentrantLock[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = new ReentrantLock();
            }
        }
        this.locations = locations;
    }

    public SimpleGameBoard(ReentrantLock[][] board, LocationOfHeroes locations) {
        this.size = board.length;
        this.board = board;
        this.locations = locations;
    }

    /**
     * Попытаться двигаться в заданном направлении.
     * @param identificationString идентификационная строка персонажа, который
     *                             желает передвинуться в двругое место на доске.
     * @param direction направление движения.
     * @param timeout длительность попытки.
     * @param unit timeUnit.
     * @return - true, если удалось передвинуться.
     * @throws InterruptedException, если поток был прерван.
     */
    public boolean tryMove(String identificationString, DirectionOfMove direction,
                           long timeout, TimeUnit unit) throws InterruptedException {
        boolean moveResult = false;
        BoardPosition currentPosition = locations.heroPosition(identificationString);
        BoardPosition newPosition = currentPosition.calculateNewPosition(direction);
        if (checkMove(newPosition)) {
            if (board[newPosition.xCoordinate()][newPosition.yCoordinate()]
                    .tryLock(timeout, unit)) {
                board[currentPosition.xCoordinate()][currentPosition.yCoordinate()]
                        .unlock();
                locations.newHeroPosition(identificationString, newPosition);
                moveResult = true;
            }
        }
        return moveResult;
    }

    /**
     * Инициализировать персонажа на игровой доске. Вызывается единожды, перед
     * тем как передвинуться на игровой доске.
     * @param identificationString - идентификационная строка персонажа.
     */
    public void initHero(String identificationString) {
        BoardPosition heroPosition = locations.heroPosition(identificationString);
        board[heroPosition.xCoordinate()][heroPosition.yCoordinate()].lock();
    }

    /**
     * Проверяет возможность движения в данную позицию на доске.
     * @param newPosition позиция на доске.
     * @return true, если передвижение в данную позицию возожно.
     */
    private boolean checkMove(BoardPosition newPosition) {
        boolean result = false;
        if (newPosition.xCoordinate() < size
                && newPosition.yCoordinate() < size) {
            if (newPosition.xCoordinate() >= 0
                    && newPosition.yCoordinate() >= 0) {
                result = true;
            }
        }
        return result;
    }
}
