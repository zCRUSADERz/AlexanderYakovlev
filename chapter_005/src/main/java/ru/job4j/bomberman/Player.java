package ru.job4j.bomberman;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Персонаж игрока.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class Player implements Runnable {
    private final GameBoard board;
    /**
     * Уникальная строка идентифицирующая любого персонажа в игре.
     */
    private final String identificationString;
    private final Random random;

    public Player(GameBoard board, String identificationString) {
        this(board, identificationString, new Random());
    }

    public Player(GameBoard board, String identificationString, Random random) {
        this.board = board;
        this.identificationString = identificationString;
        this.random = random;
    }

    /**
     * Движение в рандомном направлении. Длительность попытки движения в
     * выбранном направлении задается параметром timeout.
     * @param timeout длительность попытки сходить в заданном направлении.
     * @param unit timeUnit.
     * @return - true, если удалось передвинуться в заданном направлении.
     * @throws InterruptedException, если поток будет прерван.
     */
    public boolean randomMove(long timeout, TimeUnit unit)
            throws InterruptedException {
        int randDirection = random.nextInt(3);
        DirectionOfMove direction = DirectionOfMove.UP;
        if (randDirection == 0) {
            direction = DirectionOfMove.UP;
        } else if (randDirection == 1) {
            direction = DirectionOfMove.LEFT;
        } else if (randDirection == 2) {
            direction = DirectionOfMove.RIGHT;
        } else if (randDirection == 3) {
            direction = DirectionOfMove.DOWN;
        }
        return board.tryMove(identificationString, direction, timeout, unit);
    }

    /**
     * Эмуляция игрока.
     */
    @Override
    public void run() {
        board.initHero(identificationString);
        while (!Thread.interrupted()) {
            try {
                boolean resultMove = false;
                while (!resultMove) {
                    resultMove = randomMove(500, TimeUnit.MILLISECONDS);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
