package ru.job4j.bomberman;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование местоположений всех героев на игровой доске.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.05.2018
 */
public class LocationOfHeroesTest {
    private ConcurrentMap<String, BoardPosition> positions;
    private LocationOfHeroes locationOfHeroes;

    @Before
    public void init() {
        positions = new ConcurrentHashMap<>();
        locationOfHeroes = new LocationOfHeroes(positions);
    }

    @Test
    public void whenHeroExistInMapThenReturnHeroPosition() {
        String identification = "hero";
        BoardPosition position = new BoardPosition(0, 0);
        positions.put(identification, position);
        assertThat(locationOfHeroes.heroPosition(identification), is(position));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenHeroDoesNotExistThenThrowExeception() {
        locationOfHeroes.heroPosition("");
    }

    @Test
    public void whenNewHeroPositionThenMapHaveNewPosition() {
        String identification = "hero";
        positions.put(identification, new BoardPosition(0, 0));
        BoardPosition newPostion = new BoardPosition(5, 5);
        locationOfHeroes.newHeroPosition(identification, newPostion);
        assertThat(positions.get(identification), is(newPostion));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenHeroDoesNotExistThenNewHeroPositionThrowException() {
        locationOfHeroes.newHeroPosition("", new BoardPosition(4, 6));
    }
}