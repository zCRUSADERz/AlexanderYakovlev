package ru.job4j.observable.die;


import ru.job4j.heroes.Hero;

public interface HeroDieObservable {

    void addObserver(DieObserver observer);

    void removeObserver(DieObserver observer);

    void heroDied(Hero hero);
}
