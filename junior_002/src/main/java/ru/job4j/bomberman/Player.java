package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;

/**
 * Герой - игрок.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class Player implements Runnable {
    private final HeroPosition currentPosition;
    private final RandomDirection randomDirection;
    private final Game game;

    public Player(final HeroPosition currentPosition, final Game game) {
        this.currentPosition = currentPosition;
        this.randomDirection = new RandomDirection();
        this.game = game;
    }

    public boolean move(DirectionOfMove direction, long timeout, TimeUnit unit) throws InterruptedException {
        return this.currentPosition.moveInDirection(
                direction, timeout, unit
        );
    }

    @Override
    public void run() {
        try {
            this.currentPosition.initStartPosition();
            while (this.game.notYetStarted()) {
                Thread.sleep(10);
            }
            while (this.game.isNotFinished()) {
                if (randomMove()) {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Thread should not have been interrupted.", e);
        }
    }

    private boolean randomMove() throws InterruptedException {
        return move(this.randomDirection.nextDirection(), 500, TimeUnit.MILLISECONDS);
    }
}
