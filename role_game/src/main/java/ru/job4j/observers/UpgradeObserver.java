package ru.job4j.observers;

import ru.job4j.heroes.Hero;

import java.util.function.BiConsumer;

public interface UpgradeObserver {

    BiConsumer<Hero, UpgradeObserver> UPGRADED
            = (hero, observer) -> observer.upgraded(hero);

    void upgraded(Hero hero);
}
