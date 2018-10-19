package ru.job4j.observable.upgrade;

import ru.job4j.heroes.Hero;

public interface HeroUpgradeObservable {

    void addObserver(UpgradeObserver observer);
    void removeObserver(UpgradeObserver observer);
    void upgraded(Hero hero);
    void degraded(Hero hero);
}
