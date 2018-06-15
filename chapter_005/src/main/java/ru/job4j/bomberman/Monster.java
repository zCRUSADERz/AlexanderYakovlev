package ru.job4j.bomberman;

import java.util.concurrent.TimeUnit;

/**
 * Герой - монстр.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class Monster implements Runnable {
    private final HeroPosition currentPosition;
    private final HeroPosition playerPosition;
    private final RandomDirection randomDirection;
    private final Game game;

    public Monster(final HeroPosition currentPosition,
                   final HeroPosition playerPosition, final Game game) {
        this.currentPosition = currentPosition;
        this.playerPosition = playerPosition;
        this.randomDirection = new RandomDirection();
        this.game = game;
    }

    @Override
    public void run() {
        try {
            currentPosition.initStartPosition();
            while (game.notYetStarted()) {
                Thread.sleep(10);
            }
            while (game.isNotFinished()) {
                if (randomMove()) {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Thread should not have been interrupted.", e);
        }
    }

    private boolean randomMove() throws InterruptedException {
        boolean result = false;
        DirectionOfMove direction = this.randomDirection.nextDirection();
        if (this.currentPosition.checkOtherHeroInDirection(direction, this.playerPosition)) {
            this.game.finish();
        } else {
            result = this.currentPosition.moveInDirection(
                    direction, 500, TimeUnit.MILLISECONDS
            );
        }
        return result;
    }
}
