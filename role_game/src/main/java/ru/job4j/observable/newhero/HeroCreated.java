package ru.job4j.observable.newhero;

import ru.job4j.heroes.Hero;

import java.util.Collection;

public class HeroCreated implements HeroCreatedObservable {
    private final Collection<HeroCreatedObserver> observers;

    public HeroCreated(Collection<HeroCreatedObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(HeroCreatedObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(HeroCreatedObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void heroCreated(Hero hero) {
        this.observers.forEach(observer -> observer.heroCreated(hero));
    }
}
