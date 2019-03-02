package ru.job4j.coordinates;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * RandomBombs.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class RandomBombs {
    private final int width;
    private final int height;
    private final int bombs;
    private final Random random;

    public RandomBombs(final int width, final int height, final int bombs) {
        this(width, height, bombs, new SecureRandom());
    }

    public RandomBombs(final int width, final int height,
                       final int bombs, final Random random) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.random = random;
    }

    /**
     * Генерирует рандомные координаты игровой доски
     * с исключением переданной координаты.
     * @param excluded координата, которая не должна находится
     *                 в результирующем списке.
     * @return рандомные координаты игровой доски.
     */
    public final Iterable<Coordinate> coordinates(final Coordinate excluded) {
        final Set<Coordinate> bombsCoordinate = new HashSet<>();
        while (bombsCoordinate.size() != this.bombs) {
            final Coordinate coordinate = new Coordinate(
                    this.random.nextInt(width),
                    this.random.nextInt(height)
            );
            if (!excluded.equals(coordinate)) {
                bombsCoordinate.add(coordinate);
            }
        }
        return bombsCoordinate;
    }
}
