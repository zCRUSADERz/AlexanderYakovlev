package ru.job4j.utils;

import java.util.List;
import java.util.Random;

/**
 * Random element from list.
 * Выбирает рандомный элемент списка.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RandomElementFromList {
    private final Random random;

    public RandomElementFromList() {
        this(new Random());
    }

    public RandomElementFromList(Random random) {
        this.random = random;
    }

    /**
     * Вернет рандомный элемент из переданного списка.
     * @param list список.
     * @param <E> тип элемента.
     * @return рандомный элемент списка.
     */
    public <E> E randomElement(List<E> list) {
        return list.get(
                this.random.nextInt(
                        list.size()
                )
        );
    }
}
