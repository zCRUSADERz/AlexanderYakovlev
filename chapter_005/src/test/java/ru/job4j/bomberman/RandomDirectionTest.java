package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Рандомное направление движения. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.05.2018
 */
public class RandomDirectionTest {

    @Test
    public void whenNextDirectionThenReturnRandomDirection() {
        Random random = new Random(15768);
        RandomDirection randomDirection = new RandomDirection(random);
        assertThat(randomDirection.nextDirection(), is(DirectionOfMove.LEFT));
        assertThat(randomDirection.nextDirection(), is(DirectionOfMove.DOWN));
        assertThat(randomDirection.nextDirection(), is(DirectionOfMove.RIGHT));
        assertThat(randomDirection.nextDirection(), is(DirectionOfMove.RIGHT));
        assertThat(randomDirection.nextDirection(), is(DirectionOfMove.UP));
    }
}