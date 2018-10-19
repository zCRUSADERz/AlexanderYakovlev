package ru.job4j.observable.move;

import ru.job4j.heroes.Hero;

public interface HeroMovedObservable {

    void addObserver(HeroMovedObserver observer);

    void removeObserver(HeroMovedObserver observer);

    void heroMoved(Hero hero);
}
