package ru.job4j.bomberman;

import java.util.*;

/**
 * Рандомные стартовые местоположения героев.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.05.2018
 */
public class RandomHeroesPosition {
    private final int heroes;
    private final SimplePassablePositions positions;
    private final Random random;

    public RandomHeroesPosition(final int heroes,
                                final SimplePassablePositions positions) {
        this.heroes = heroes;
        this.positions = positions;
        this.random = new Random();
    }

    /**
     * Возвращает коллекцию стартовых рандомных местоположений.
     * @return коллекция стартовых местоположений.
     */
    public Collection<Position> generate() {
        Collection<Position> result = new ArrayList<>(heroes);
        List<Position> passablePositions = new ArrayList<>(
                positions.allPassablePositions()
        );
        for (Integer index : randomIndex(passablePositions.size())) {
            result.add(passablePositions.get(index));
        }
        return result;
    }

    private Set<Integer> randomIndex(int maxIndex) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < this.heroes; i++) {
            int index;
            do {
                index = this.random.nextInt(maxIndex);
            } while (!result.add(index));
        }
        return result;
    }
}
