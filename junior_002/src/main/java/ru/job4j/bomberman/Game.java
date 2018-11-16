package ru.job4j.bomberman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Игра бомбермэн.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.06.2018
 */
public class Game {
    private final Collection<Thread> threads = new ArrayList<>();
    private volatile boolean started = false;
    private volatile boolean finished = false;

    public static void main(String[] args) throws InterruptedException {
        new Game().start();
    }

    /**
     * Запуск игры.
     * @throws InterruptedException, если поток был прерван.
     */
    public void start() throws InterruptedException {
        int boardSize = 10;
        int player = 1;
        int numberOfMonsters = 5;
        SimplePassablePositions passablePositions = new SimplePassablePositions(
                new BoardPositions(boardSize)
        );
        ReentrantLock[][] locks = initLocks(boardSize, passablePositions);
        GameBoard board = new SimpleGameBoard(locks);
        RandomHeroesPosition randomHeroesPosition = new RandomHeroesPosition(
                player + numberOfMonsters,
                passablePositions
        );
        Collection<Position> heroPositions = randomHeroesPosition.generate();
        Iterator<Position> iterator = heroPositions.iterator();
        HeroPosition playerPosition = new HeroPosition(
                iterator.next(),
                board,
                passablePositions
        );
        this.threads.add(
                new Thread(
                        new Player(
                                playerPosition,
                                this
                        )
                )
        );
        Collection<HeroPosition> monstersPositions = new ArrayList<>(numberOfMonsters);
        for (int i = 0; i < numberOfMonsters; i++) {
            HeroPosition monsterPosition = new HeroPosition(
                    iterator.next(),
                    board,
                    passablePositions
            );
            monstersPositions.add(monsterPosition);
            this.threads.add(
                    new Thread(
                            new Monster(
                                    monsterPosition,
                                    playerPosition,
                                    this
                            )
                    )
            );
        }
        for (Thread thread : this.threads) {
            thread.start();
        }
        while (!isLockedStartPosition(locks, heroPositions)) {
            Thread.sleep(1);
        }
        this.started = true;
        new GameScreen(this, playerPosition, monstersPositions, boardSize, passablePositions).show();
    }

    /**
     * Завершить игру. Игрок был пойман монстрами.
     */
    public void finish() {
        this.finished = true;
    }

    /**
     * Игра еще не началась.
     * @return true, если игра не началась.
     */
    public boolean notYetStarted() {
        return !this.started;
    }

    /**
     * Игра еще не закончилась.
     * @return true, если не закончилась.
     */
    public boolean isNotFinished() {
        return !this.finished;
    }

    private boolean isLockedStartPosition(ReentrantLock[][] locks, Collection<Position> heroPositions) {
        boolean result = true;
        for (Position pos : heroPositions) {
            if (pos.getArrElement(locks).isLocked()) {
                result = false;
            }
        }
        return result;
    }

    private ReentrantLock[][] initLocks(int boardSize, SimplePassablePositions passablePositions) {
        ReentrantLock[][] locks = new ReentrantLock[boardSize][boardSize];
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                locks[x][y] = new ReentrantLock();
            }
        }
        Collection<Position> passablePos = passablePositions.impassablePositions();
        for (Position pos : passablePos) {
            pos.getArrElement(locks).lock();
        }
        return locks;
    }
}
