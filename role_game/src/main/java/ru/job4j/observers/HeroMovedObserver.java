package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface HeroMovedObserver {

    BiConsumer<Hero, HeroMovedObserver> MOVED
            = (hero, observer) -> observer.heroMoved(hero);

    void heroMoved(Hero hero);
}
