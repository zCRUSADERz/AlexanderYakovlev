package ru.job4j.observable.die;

import ru.job4j.heroes.Hero;

import java.util.Collection;

public class HeroDied implements HeroDiedObservable {
    private final Collection<HeroDiedObserver> observers;

    public HeroDied(Collection<HeroDiedObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(HeroDiedObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(HeroDiedObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void heroDied(Hero hero) {
        this.observers.forEach(observer -> observer.heroDied(hero));
    }
}
