package ru.job4j.observable.die;


import ru.job4j.heroes.Hero;

public interface HeroDiedObservable {

    void addObserver(HeroDiedObserver observer);

    void removeObserver(HeroDiedObserver observer);

    void heroDied(Hero hero);
}
