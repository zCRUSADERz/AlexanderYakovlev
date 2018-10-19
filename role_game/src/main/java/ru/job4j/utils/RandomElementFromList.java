package ru.job4j.utils;

import java.util.List;
import java.util.Random;

public class RandomElementFromList {
    private final Random random;

    public RandomElementFromList(Random random) {
        this.random = random;
    }

    public <E> E randomElement(List<E> list) {
        return list.get(
                this.random.nextInt(
                        list.size()
                )
        );
    }
}
