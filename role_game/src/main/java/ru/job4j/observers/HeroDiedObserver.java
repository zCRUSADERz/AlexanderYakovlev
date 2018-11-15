package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface HeroDiedObserver {

    BiConsumer<Hero, HeroDiedObserver> DIED
            = (hero, observer) -> observer.heroDied(hero);

    void heroDied(Hero hero);
}
