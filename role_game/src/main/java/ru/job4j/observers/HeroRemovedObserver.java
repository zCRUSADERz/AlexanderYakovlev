package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface HeroRemovedObserver {

    BiConsumer<Hero, HeroRemovedObserver> REMOVED
            = (hero, observer) -> observer.heroRemoved(hero);


    void heroRemoved(Hero hero);
}
