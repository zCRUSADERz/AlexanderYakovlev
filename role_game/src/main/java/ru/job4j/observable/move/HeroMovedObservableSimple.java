package ru.job4j.observable.move;

import ru.job4j.heroes.Hero;

import java.util.ArrayList;
import java.util.Collection;

public class HeroMovedObservableSimple implements HeroMovedObservable {
    private final Collection<HeroMovedObserver> observers;

    public HeroMovedObservableSimple() {
        this(new ArrayList<>());
    }

    public HeroMovedObservableSimple(Collection<HeroMovedObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(HeroMovedObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(HeroMovedObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void heroMoved(Hero hero) {
        this.observers.forEach(observer -> observer.heroMoved(hero));
    }
}