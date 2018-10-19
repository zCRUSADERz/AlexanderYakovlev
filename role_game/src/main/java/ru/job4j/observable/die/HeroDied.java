package ru.job4j.observable.die;

import ru.job4j.heroes.Hero;

import java.util.Collection;

public class HeroDied implements HeroDieObservable {
    private final Collection<DieObserver> observers;

    public HeroDied(Collection<DieObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(DieObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(DieObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void heroDied(Hero hero) {
        this.observers.forEach(observer -> observer.heroDied(hero));
    }
}
