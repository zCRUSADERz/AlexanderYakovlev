package ru.job4j.observable.upgrade;

import ru.job4j.heroes.Hero;

public interface UpgradeObserver {

    void upgraded(Hero hero);

    void degraded(Hero hero);
}
