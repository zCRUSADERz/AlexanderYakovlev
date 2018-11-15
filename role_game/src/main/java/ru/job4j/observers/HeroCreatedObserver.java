package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface HeroCreatedObserver {

    BiConsumer<Hero, HeroCreatedObserver> CREATED
            = (hero, observer) -> observer.heroCreated(hero);

    void heroCreated(Hero hero);
}
