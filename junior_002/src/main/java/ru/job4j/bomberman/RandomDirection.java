package ru.job4j.bomberman;

import java.util.Random;

/**
 * Рандомное направление движения.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.05.2018
 */
public class RandomDirection {
    private final Random random;

    public RandomDirection() {
        this(new Random());
    }

    public RandomDirection(Random random) {
        this.random = random;
    }

    /**
     * Возвращает следующее рандомное направление.
     * @return рандомное направление.
     */
    public DirectionOfMove nextDirection() {
        DirectionOfMove[] directions = DirectionOfMove.values();
        int randIndex = random.nextInt(directions.length);
        return directions[randIndex];
    }
}
