package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Персонаж игрока. Тест.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class PlayerTest {

    @Test
    public void whenRandomMoveSuccessThenReturnTrue() throws InterruptedException {
        String playerIdentificationString = "player";
        DirectionOfMove playerDirection = DirectionOfMove.UP;
        GameBoard board = new GameBoard() {
            @Override
            public boolean tryMove(String identificationString, DirectionOfMove direction, long timeout, TimeUnit unit) {
                assertThat(identificationString, is(playerIdentificationString));
                assertThat(direction, is(playerDirection));
                assertThat(timeout, is(500L));
                assertThat(unit, is(TimeUnit.MILLISECONDS));
                return true;
            }

            @Override
            public void initHero(String identificationString) {
            }
        };
        Player player = new Player(board, playerIdentificationString, new Random(0));
        boolean result = player.randomMove(500, TimeUnit.MILLISECONDS);
        assertThat(result, is(true));
    }
}