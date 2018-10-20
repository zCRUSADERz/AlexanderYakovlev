package ru.job4j.observable.newhero;

import ru.job4j.heroes.Hero;

import java.util.ArrayList;
import java.util.Collection;

public class HeroCreatedObservableSimple implements HeroCreatedObservable {
    private final Collection<HeroCreatedObserver> observers;

    public HeroCreatedObservableSimple() {
        this(new ArrayList<>());
    }

    public HeroCreatedObservableSimple(Collection<HeroCreatedObserver> observers) {
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
