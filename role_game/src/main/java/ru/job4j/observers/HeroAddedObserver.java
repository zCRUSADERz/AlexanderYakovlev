package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface HeroAddedObserver {

    BiConsumer<Hero, HeroAddedObserver> ADDED
            = (hero, observer) -> observer.heroAdded(hero);

    void heroAdded(Hero hero);
}
