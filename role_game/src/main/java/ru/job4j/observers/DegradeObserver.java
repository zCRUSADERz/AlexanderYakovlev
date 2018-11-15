package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface DegradeObserver {

    BiConsumer<Hero, DegradeObserver> DEGRADED
            = (hero, degradeObserver) -> degradeObserver.degraded(hero);

    void degraded(Hero hero);
}
