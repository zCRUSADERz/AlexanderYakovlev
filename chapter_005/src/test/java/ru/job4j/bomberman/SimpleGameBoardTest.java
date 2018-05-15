package ru.job4j.bomberman;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Игровая доска. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class SimpleGameBoardTest {
    private String playerIdentification = "player";
    private BoardPosition startPlayerPosition = new BoardPosition(3, 4);
    private ConcurrentMap<String, BoardPosition> positions;
    private LocationOfHeroes locationOfHeroes;
    private SimpleGameBoard board;
    private ReentrantLock[][] boardLocks;
    private final int boardSize = 10;

    @Before
    public void init() {
        positions = new ConcurrentHashMap<>();
        positions.put(playerIdentification, startPlayerPosition);
        locationOfHeroes = new LocationOfHeroes(positions);
        boardLocks = new ReentrantLock[boardSize][boardSize];
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                boardLocks[x][y] = new ReentrantLock();
            }
        }
    }

    @Test
    public void whenTryMoveBeyondTheBoardThenReturnFalse() throws InterruptedException {
        board = new SimpleGameBoard(5, locationOfHeroes);
        locationOfHeroes.newHeroPosition(playerIdentification, new BoardPosition(4, 4));
        boolean result = board.tryMove(playerIdentification, DirectionOfMove.UP, 1, TimeUnit.MILLISECONDS);
        assertThat(result, is(false));
        result = board.tryMove(playerIdentification, DirectionOfMove.RIGHT, 1, TimeUnit.MILLISECONDS);
        assertThat(result, is(false));
        locationOfHeroes.newHeroPosition(playerIdentification, new BoardPosition(0, 0));
        result = board.tryMove(playerIdentification, DirectionOfMove.LEFT, 1, TimeUnit.MILLISECONDS);
        assertThat(result, is(false));
        result = board.tryMove(playerIdentification, DirectionOfMove.DOWN, 1, TimeUnit.MILLISECONDS);
        assertThat(result, is(false));
    }

    @Test
    public void whenLockNotLockedThenTryMoveReturnTrue() throws InterruptedException {
        board = new SimpleGameBoard(boardLocks, locationOfHeroes);
        board.initHero(playerIdentification);
        boolean result = board.tryMove(
                playerIdentification, DirectionOfMove.RIGHT, 1, TimeUnit.MILLISECONDS
        );
        assertThat(result, is(true));
        BoardPosition resultPosition = locationOfHeroes.heroPosition(playerIdentification);
        assertThat(resultPosition.xCoordinate(), is(4));
        assertThat(resultPosition.yCoordinate(), is(4));
        assertThat(boardLocks[3][4].isLocked(), is(false));
        assertThat(boardLocks[4][4].isLocked(), is(true));
    }
}