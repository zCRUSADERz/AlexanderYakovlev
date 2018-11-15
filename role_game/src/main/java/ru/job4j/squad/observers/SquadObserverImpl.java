package ru.job4j.squad.observers;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.Observables;
import ru.job4j.observers.SquadObserver;

public class SquadObserverImpl<T> implements SquadObserver {
    private final String observerName;
    private final Observables<T> observables;
    private final T observer;

    public SquadObserverImpl(String observerName,
                             Observables<T> observables, T observer) {
        this.observerName = observerName;
        this.observables = observables;
        this.observer = observer;
    }

    @Override
    public void heroAdded(Hero hero) {
        this.observables.addObserverFor(hero, this.observer);
    }

    @Override
    public void heroRemoved(Hero hero) {
        this.observables.removeObserverFor(hero, this.observer);
    }

    @Override
    public String toString() {
        return this.observerName;
    }
}
